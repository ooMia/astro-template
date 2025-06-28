package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class _03_StringConcatenationTests {
  @Test
  void testEx1() {
    var target = new _03_StringConcatenation.Ex1();

    assertEquals("Entry#21", target.before(2));
    assertEquals("Entry#3", target.after(2));
  }
}