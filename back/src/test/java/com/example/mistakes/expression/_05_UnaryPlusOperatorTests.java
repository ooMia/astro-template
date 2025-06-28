package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class _05_UnaryPlusOperatorTests {
  @Test
  void testEx1() {
    var target = new _05_UnaryPlusOperator.Ex1();

    String user = "Tom";

    String wrong = "User not found: %d%s%c".formatted((int) '\"', user, '\"');
    assertEquals(wrong, target.before(user));

    String correct = "User not found: \"%s\"".formatted(user);
    assertEquals(correct, target.after(user));
  }

  @Test
  void testEx2() {
    var target = new _05_UnaryPlusOperator.Ex2();

    assertEquals(7, target.before(3, 7));
    assertEquals(10, target.after(3, 7));
  }
}