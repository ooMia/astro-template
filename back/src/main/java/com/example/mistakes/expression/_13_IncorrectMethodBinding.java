package com.example.mistakes.expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _13_IncorrectMethodBinding {
  _13_IncorrectMethodBinding(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class);
    service.add(entities);
  }

  static class Ex1 {
    class Storage {
      protected final Map<String, StringBuilder> contents = new TreeMap<>();

      void add(String fileName, String line) {
        contents.computeIfAbsent(fileName, StringBuilder::new).append(line)
            .append("\n");
      }

      String dump() {
        StringBuilder sb = new StringBuilder();
        contents.forEach((fileName, content) -> {
          sb.append("File name: ").append(fileName).append("\n");
          sb.append("Content:\n").append(content).append("\n");
        });
        return sb.toString();
      }
    }

    String before(Storage storage) {
      storage.add("users.txt", "admin");
      storage.add("users.txt", "guest");
      storage.add("numbers.txt", "3.1415");
      return storage.dump();
    }

    String after() {
      class NewStorage extends Storage {
        @Override
        void add(String fileName, String line) {
          contents.computeIfAbsent(fileName, _ -> new StringBuilder())
              .append(line).append("\n");
        }
      }
      return before(new NewStorage());
    }
  }

  static class Ex2 {
    class Utils {
      @SafeVarargs
      static <T> HashSet<T> newHashSet(T... elements) {
        return new HashSet<>(Arrays.asList(elements));
      }
    }

    Set<String> before(String key, String value) {
      Map<String, Set<String>> map = new HashMap<>();
      map.computeIfAbsent(key, Utils::newHashSet).add(value);
      return map.get(key);
    }

    Set<String> after(String key, String value) {
      Map<String, Set<String>> map = new HashMap<>();
      map.computeIfAbsent(key, _ -> Utils.newHashSet()).add(value);
      return map.get(key);
    }
  }

  static class Ex3 {
    StringBuilder[] before(int size) {
      StringBuilder[] array = new StringBuilder[size];
      Arrays.setAll(array, StringBuilder::new);
      return array;
    }

    StringBuilder[] after(int size) {
      StringBuilder[] array = new StringBuilder[size];
      Arrays.setAll(array, _ -> new StringBuilder());
      return array;
    }
  }
}