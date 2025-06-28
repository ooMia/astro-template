package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _05_UnaryPlusOperator {

  _05_UnaryPlusOperator(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class, Ex2.class);
    service.addAll(entities);
  }

  static class Ex1 {
    // @formatter:off
    String before(String userName) {
      return "User not found: " +
            + '"' + userName + '"';
    }

    String after(String userName) {
      return "User not found: "
            + '"' + userName + '"';
    }
    // @formatter:on
  }

  static class Ex2 {
    int before(int x, int y) {
      // @formatter:off
      return x =+ y;
      // @formatter:on
    }

    int after(int x, int y) {
      return x += y;
    }
  }
}
