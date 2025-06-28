package com.example.mistakes.api.questions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.mistakes.base.template.ResponseManyDTO;
import com.example.mistakes.base.type.FsMeta;
import com.example.mistakes.base.type.Identifiable;
import com.example.mistakes.base.type.Message;

import lombok.extern.log4j.Log4j2;

final class ResClassQuestion extends ResponseManyDTO<QuestionEntity> {
  ResClassQuestion(Iterable<QuestionEntity> result) {
    super(result);
  }
}

// TODO: refactor to class
@Log4j2
public record QuestionEntity(String message)
    implements Message, Identifiable<String>, FsMeta {

  private static Map<String, Integer> chapterMap = new HashMap<>(
      Map.of("expression", 2));

  public int getChapter() {
    var topLevelPackage = message.split("\\.")[3];
    return chapterMap.getOrDefault(topLevelPackage, 0);
  }

  public String getId() {
    var topLevelPackage = message.split("\\.")[3];
    var chapter = chapterMap.getOrDefault(topLevelPackage, 0);

    Pattern pattern = Pattern.compile("_(\\d+)_\\S+(\\d+)");
    Matcher matcher = pattern.matcher(message);
    if (matcher.find()) {
      String mistakeId = matcher.group(1);
      String index = matcher.group(2);
      return "%s_%s_%s".formatted(chapter, mistakeId, index);
    }

    // legacy: return 1 when got
    // `com/example/mistakes/expression/_01_OperationPriority.java`
    String filtered = message.replaceAll("[^0-9]", "");
    if (filtered.isEmpty()) {
      return String.valueOf(message.hashCode());
    }
    return String.valueOf(Integer.parseInt(filtered));
  }

  public Path getPath() {
    // return =
    // src/main/java/com/example/mistakes/expression/_01_OperationPriority.java
    // when message = com.example.mistakes.expression._01_OperationPriority.Ex2

    String pathPrefix = "src/main/java";
    String fileExtension = ".java";

    Pattern pattern = Pattern.compile("(\\S+_\\d+_[^.]+)");
    Matcher matcher = pattern.matcher(message);

    String path;
    if (matcher.find()) {
      String group = matcher.group(1);
      path = group.replace(".", "/") + fileExtension;
    } else {
      throw new IllegalArgumentException(
          "Matcher not found: message=" + message);
    }
    return Paths.get(pathPrefix, path);
  }

  public String getClassName() {
    // return = Ex2
    // when message = com.example.mistakes.expression._01_OperationPriority.Ex2

    // temporary assume there is only one nested class
    return message.substring(message.lastIndexOf(".") + 1);
  }

  public String getBefore() {
    return Code.readMethodCode(getPath(), getClassName(), "before");
  }

  public String getAfter() {
    return Code.readMethodCode(getPath(), getClassName(), "after");
  }

  private class Code {
    static String readMethodCode(Path filePath, String className,
        String methodName) {
      try {
        return read(filePath, className, methodName);
      } catch (Exception e) {
        return "NoSuchFileException path=%s className=%s methodName=%s"
            .formatted(filePath, className, methodName);
      }
    }

    private static String read(Path filePath, String className,
        String methodName) throws IOException {

      List<String> classCode = findClassCode(filePath, className);

      StringBuilder methodCode = new StringBuilder();
      boolean methodFound = false;
      for (String line : classCode) {
        if (line.contains(" " + methodName + "(")) {
          // Simple check for method signature
          methodFound = true;
          methodCode.append(line).append("\n");
        } else if (methodFound && line.contains("}")) {
          methodCode.append(line).append("\n");
          break; // Stop reading after the closing brace of the method
        } else if (methodFound) {
          methodCode.append(line).append("\n");
        }
      }
      return methodCode.toString();
    }

    private static List<String> findClassCode(Path filePath, String className)
        throws IOException {
      List<String> classCode = new ArrayList<>();

      boolean classFound = false;
      int braceCount = 0;
      for (String line : Files.readAllLines(filePath)) {
        if (line.contains("class " + className)) {
          // Simple check for class signature
          classFound = true;
          classCode.add(line);
          braceCount++;
        } else if (classFound) {
          classCode.add(line);
          if (line.contains("{")) {
            braceCount++;
          }
          if (line.contains("}")) {
            braceCount--;
            if (braceCount == 0) {
              // Stop reading after the closing brace of the class
              break;
            }
          }
        }
      }
      return classCode;
    }
  }
}
