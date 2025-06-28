package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _12_UnusedObjects {
  _12_UnusedObjects(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class);
    service.add(entities);
  }

  static class Ex1 {
    void before(int value) {
      if (value < 0) {
        new IllegalArgumentException("Value is negative");
      }
    }
   
    void after(int value) throws IllegalArgumentException {
      if (value < 0) {
        throw new IllegalArgumentException("Value is negative");
      }
    }
  }
}
