package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _08_MixedLogicalOperators {

  _08_MixedLogicalOperators(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class, Ex2.class, Ex3.class,
        Ex4.class);
    service.addAll(entities);
  }

  static class Ex1 {
    // throw exception if value is not in range [0, 255]

    void before(int byteValue) {
      if (byteValue < 0 && byteValue > 255) {
        throw new IllegalArgumentException("byteValue is out of range");
      }
    }

    void after(int byteValue) {
      if (byteValue < 0 || byteValue > 255) {
        throw new IllegalArgumentException("byteValue is out of range");
      }
    }
  }

  static class Ex2 {
    // return true if all bytes are printable ASCII characters [0x20, 0x7E]
    // except for tab(\t) and newline(\n)

    boolean before(byte[] array) {
      for (byte b : array) {
        char c = (char) (b & 0xFF);
        if (c != '\t' && c != '\n' && c < 0x20 && c > 0x7E) {
          return false;
        }
      }
      return true;
    }

    boolean after(byte[] array) {
      for (byte b : array) {
        char c = (char) (b & 0xFF);
        if (c != '\t' && c != '\n' && (c < 0x20 || c > 0x7E)) {
          return false;
        }
      }
      return true;
    }
  }

  static class Ex3 {
    // return true if not a comment

    boolean before(String line) {
      return !line.startsWith("#") || !line.startsWith("//");
    }

    boolean after(String line) {
      return !line.startsWith("#") && !line.startsWith("//");
    }
  }

  static class Ex4 {
    // throw exception if value is not in range [10, 20]

    void before(int value) {
      int abs = Math.abs(value);
      if (abs < 10 && abs > 20) {
        throw new IllegalArgumentException();
      }
    }

    void after(int value) {
      int abs = Math.abs(value);
      if (abs < 10 || abs > 20) {
        throw new IllegalArgumentException();
      }
    }
  }
}
