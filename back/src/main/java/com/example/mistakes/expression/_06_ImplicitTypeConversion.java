package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _06_ImplicitTypeConversion {

  _06_ImplicitTypeConversion(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class, Ex2.class);
    service.addAll(entities);
  }

  static class Ex1 {
    String subContext = "Primitive Wrapped on Ternary";

    Double before(boolean condition, Double value) {
      return condition ? value : 0.0;
      // return Double.valueOf( condition ? value.doubleValue() : 0.0 );
    }

    Double after(boolean condition, Double value) {
      // if (condition) {
      // return value;
      // } else {
      // return 0.0;
      // }
      return condition ? value : Double.valueOf(0.0);
    }
  }

  static class Ex2 {
    String context = "Nested Ternary";

    @SuppressWarnings("null")
    Integer before(int input) {
      return input > 20 ? 2 : input > 10 ? 1 : null;
      // return Integer.valueOf(input > 20 ? 2 : (input > 10 ?
      // Integer.valueOf(1) : null).intValue());
    }

    Integer after(int input) {
      return input <= 10 ? null : input <= 20 ? 1 : 2;
      // return input <= 10 ? null : Integer.valueOf(input <= 20 ? 1 : 2);
    }
  }
}
