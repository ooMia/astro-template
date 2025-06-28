package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _10_TernaryWithVarArgs {

  _10_TernaryWithVarArgs(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class);
    service.add(entities);
  }

  static class Ex1 {
    // instead of sout, return String
    String before(String formatString, Object... params) {

      // before refactoring
      // if (params.length == 0) {
      // return String.format(formatString, "user");
      // } else {
      // return String.format(formatString, params);
      // }

      return String.format(formatString, params.length == 0 ? "user" : params);
    }

    String after(String formatString, Object... params) {
      var defaultParam = new Object[] { "user" };
      return String.format(formatString,
          params.length == 0 ? defaultParam : params);
    }
  }
}
