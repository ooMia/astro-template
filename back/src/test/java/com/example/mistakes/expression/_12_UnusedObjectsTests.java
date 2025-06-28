package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class _12_UnusedObjectsTests {

  @Test
  void testEx1() {
    var target = new _12_UnusedObjects.Ex1();
    assertDoesNotThrow(() -> target.before(-1));
    assertThrows(IllegalArgumentException.class, () -> target.after(-1));
  }
}
