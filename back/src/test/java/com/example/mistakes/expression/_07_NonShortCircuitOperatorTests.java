package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class _07_NonShortCircuitOperatorTests {
  @Test
  void testEx1() {
    var target = new _07_NonShortCircuitOperator.Ex1();

    assertThrows(ArrayIndexOutOfBoundsException.class, () -> target.before(new int[] { 1, 2, 3 }, 4));
    assertFalse(target.after(new int[] { 1, 2, 3 }, 4));
  }

  @Test
  void testEx2() {
    var target = new _07_NonShortCircuitOperator.Ex2();

    assertEquals(target.before(), target.after());
    {
      int count = target.count;
      assertFalse(target.before());
      assertEquals(3, target.count - count);
    }
    {
      int count = target.count;
      assertFalse(target.after());
      assertEquals(1, target.count - count);
    }
  }

  @Test
  void testEx3() {
    var target = new _07_NonShortCircuitOperator.Ex3();
    var second = new _07_NonShortCircuitOperator.Ex3.Second() {
    };

    assertFalse(target.before(second));
    assertTrue(target.after(second));
  }
}