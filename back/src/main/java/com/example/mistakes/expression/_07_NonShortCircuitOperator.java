package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _07_NonShortCircuitOperator {

  _07_NonShortCircuitOperator(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class, Ex2.class, Ex3.class);
    service.addAll(entities);
  }

  static class Ex1 {
    boolean before(int[] data, int index) {
      return index >= 0 && index < data.length & data[index] > 0;
    }

    boolean after(int[] data, int index) {
      return index >= 0 && index < data.length && data[index] > 0;
    }
  }

  static class Ex2 {
    // @formatter:off
    int count = 0;

    boolean check1() { count++; return false; }
    boolean check2() { count++; return false; }
    boolean check3() { count++; return false; }
    // @formatter:on

    boolean before() {
      boolean result = true;
      result &= check1();
      result &= check2();
      result &= check3();
      return result;
    }

    boolean after() {
      boolean result = true;
      result = result && check1();
      result = result && check2();
      result = result && check3();
      return result;
    }
  }

  static class Ex3 {
    // @formatter:off
    interface First {}
    interface Second {}
    interface Third {}
    interface Exclude extends Second {}

    static boolean checkFirst(First obj) { return obj instanceof First; }
    static boolean checkThird(Third obj) { return obj instanceof Third; }
    // @formatter:on

    boolean before(Object obj) {
      return obj instanceof First
          && checkFirst((First) obj) | obj instanceof Second
          && !(obj instanceof Exclude)
          || obj instanceof Third && checkThird((Third) obj);
    }

    boolean after(Object obj) {
      return switch (obj) { // Java 21
      case First first -> checkFirst(first);
      case Second second -> !(second instanceof Exclude);
      case Third third -> checkThird(third);
      case null, default -> false;
      };
    }
  }

  static class Ex4 {
    // refactor usage of non-short-circuit operator

    // @formatter:off
    boolean a = false, b = false;

    boolean updateA() { return a = true; }
    boolean updateB() { return b = true; }
    // @formatter:on

    boolean before() {
      return updateA() & updateB();
    }

    boolean after() {
      boolean isAUpdated = updateA();
      boolean isBUpdated = updateB();
      return isAUpdated && isBUpdated;
    }
  }
}
