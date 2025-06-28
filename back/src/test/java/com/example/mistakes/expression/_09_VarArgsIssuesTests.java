package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class _09_VarArgsIssuesTests {
  @Test
  void testEx1() {
    var target = new _09_VarArgsIssues.Ex1();

    var expect = "Hello\nWorld";
    Object[] objArr = { "Hello", "World" };
    Object obj = new Object[] { "Hello", "World" };

    assertEquals(expect, target.before(objArr));
    assertEquals(expect, target.after(objArr));

    assertNotEquals(expect, target.before(obj));
    assertEquals(expect, target.after(obj));

    Object[] nullArr = null;
    Object[] emptyArr = new Object[] {};

    assertThrows(NullPointerException.class, () -> target.before(nullArr));
    assertEquals("", target.after(nullArr));

    assertEquals("", target.before(emptyArr));
    assertEquals("", target.after(emptyArr));
  }

  @Test
  void testEx2() {
    var target = new _09_VarArgsIssues.Ex2();

    assertFalse(target.before("Java"));
    assertTrue(target.after("Java"));
  }

  @Test
  void testEx3() {
    var target = new _09_VarArgsIssues.Ex3();
    var arrayWithZero = new int[] { 0 };

    assertFalse(target.before(arrayWithZero));
    assertTrue(target.after(arrayWithZero));
  }

}