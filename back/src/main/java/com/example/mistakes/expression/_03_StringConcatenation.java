package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _03_StringConcatenation {

  _03_StringConcatenation(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class);
    service.add(entities);
  }

  static class Ex1 {
    String before(int index) {
      return "Entry#" + index + 1;
    }

    String after(int index) {
      int adjustIndex = index + 1;
      return "Entry#" + adjustIndex;
    }
  }
}
