package com.example.mistakes.expression;

import static java.util.Objects.requireNonNullElse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _02_MissingParentheses {

  _02_MissingParentheses(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class, Ex2.class, Ex3.class,
        Ex4.class);
    service.addAll(entities);
  }

  static class Ex1 {
    String subContext = "Logical operator precedence";

  // @formatter:off
    boolean before(int index, String str) {
      return index >= 0
          && str.charAt(index) == ' '
          || str.charAt(index) == '\t';
    }
  // @formatter:on

    boolean after(int index, String str) {
      return index >= 0
          && (str.charAt(index) == ' ' || str.charAt(index) == '\t');
    }
  }

  static class Ex2 {
    String subContext = "Ternary & addition";

    StringBuilder before(String str, int indent) {
      int capacity = str.length() + indent < 0 ? 0 : indent;
      return new StringBuilder(capacity);
    }

    StringBuilder after(String str, int indent) {
      int capacity = str.length() + Math.max(indent, 0);
      return new StringBuilder(capacity);
    }
  }

  static class Ex3 {
    String subContext = "Ternary & null check";

    String before(String value) {
      return "Value: " + value != null ? value : "(unknown)";
    }

    String after(String value) {
      return "Value: " + requireNonNullElse(value, "(unknown)");
    }
  }

  static class Ex4 {
    String subContext = "Ternary & null check";

    int before(List<String> input, String newItem) {
      return input.size() + newItem == null ? 0 : 1;
    }

    int after(List<String> input, String newItem) {
      int additionalElements = newItem == null ? 0 : 1;
      return input.size() + additionalElements;
    }
  }
}
