package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _01_OperationPriority {

  _01_OperationPriority(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class, Ex2.class, Ex3.class,
        Ex4.class);
    service.addAll(entities);
  }

  static class Ex1 {
    String subContext = "Binary Shift";

    int before(short lo, short hi) {
      return lo << 16 + hi;
    }

    int after(short lo, short hi) {
      return (lo << 16) + hi;
    }
  }

  static class Ex2 {
    String subContext = "Binary Shift";
    short xmin = 1, ymin = 1, xmax = 1, ymax = 1;

    int before() {
      return xmin + ymin << 8 + xmax << 16 + ymax << 24;
    }

    int after() {
      return xmin + (ymin << 8) + (xmax << 16) + (ymax << 24);
    }
  }

  static class Ex3 {
    String subContext = "Binary Shift";
    int BLOCK_SIZE = 64 * 1024;

    int before() {
      return BLOCK_SIZE + BLOCK_SIZE >> 2;
    }

    int after() {
      return BLOCK_SIZE + (BLOCK_SIZE >> 2);
    }
  }

  static class Ex4 {
    String subContext = "Bitwise Operator";

    int before(int bits) {
      return bits & 0xFF00 + 1;
    }

    int after(int bits) {
      return (bits & 0xFF00) | 1;
    }
  }
}
