package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class _06_ImplicitTypeConversionTests {
  @Test
  void testEx1() {
    var target = new _06_ImplicitTypeConversion.Ex1();

    assertThrows(NullPointerException.class, () -> target.before(true, null));
    assertEquals(null, target.after(true, null));
  }

  @Test
  void testEx2() {
    var target = new _06_ImplicitTypeConversion.Ex2();

    assertThrows(NullPointerException.class, () -> target.before(9));
    assertEquals(null, target.after(9));
  }
}