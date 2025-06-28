package com.example.mistakes.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _09_VarArgsIssues {
  _09_VarArgsIssues(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class);
    service.add(entities);
  }

  static class Ex1 {
    // instead of print sout, return joined string
    String context = "Ambiguous var args";

    static List<String> stringifyAll(Object... data) {
      var result = new ArrayList<String>();
      for (Object d : data) {
        result.add(d.toString());
      }
      return result;
    }

    String before(Object... data) {
      return String.join("\n", stringifyAll(data));
    }

    String after(Object... data) {
      if (data == null || data.length == 0) {
        return "";
      }
      if (data.length == 1 && data[0] instanceof Object[] data2) {
        return String.join("\n", stringifyAll(data2));
      }
      return String.join("\n", stringifyAll(data));
    }
  }

  static class Ex2 {
    String context = "Array & Collection Mix-up";

    @SafeVarargs
    static <T> boolean contains(T needle, T... haystack) {
      for (T t : haystack) {
        if (Objects.equals(t, needle)) {
          return true;
        }
      }
      return false;
    }

    boolean before(String language) {
      List<String> allLanguages = List.of("Java", "Groovy", "Scala", "Kotlin");
      return contains(language, allLanguages);
    }

    boolean after(String language) {
      String[] allLanguages = { "Java", "Groovy", "Scala", "Kotlin" };
      return contains(language, allLanguages);
    }
  }

  static class Ex3 {
    // check if the array contains 0
    String context = "Primitive Array To VarArgs";

    @SuppressWarnings("unlikely-arg-type")
    boolean before(int[] arr) {
      return Arrays.asList(arr).contains(0);
    }

    boolean after(int[] arr) {
      List<Integer> list = Arrays.stream(arr).boxed().toList();
      return list.contains(0);
    }
  }
}
