package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class _14_WrongMethodReferenceTests {

  @Test
  void testEx1() {
    var target = new _14_WrongMethodReference.Ex1();

    {
      var expect = List.of(0, -1, -2, -3, 3, 1, 2);
      assertEquals(expect, target.before());
    }
    {
      var expect = List.of(-3, -2, -1, 0, 1, 2, 3);
      assertEquals(expect, target.after());
    }
  }

}
