package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class _02_MissingParenthesesTests {

  @Test
  void testEx1() {
    var target = new _02_MissingParentheses.Ex1();

    assertThrows(StringIndexOutOfBoundsException.class, () -> target.before(-1, " "));
    assertDoesNotThrow(() -> target.after(-1, " "));

    assertTrue(target.after(0, "\t"));
    assertTrue(target.after(1, "a "));
  }

  @Test
  void testEx2() {
    var target = new _02_MissingParentheses.Ex2();

    assertThrows(NegativeArraySizeException.class, () -> target.before("abc", -1));
    assertDoesNotThrow(() -> target.after("abc", -1));

    assertEquals(3, target.after("abc", 0).capacity());
  }

  @Test
  void testEx3() {
    var target = new _02_MissingParentheses.Ex3();
    var expectWhenNull = "Value: (unknown)";

    assertNotEquals(expectWhenNull, target.before(null));
    assertEquals(expectWhenNull, target.after(null), expectWhenNull);
  }

  @Test
  void testEx4() {
    var target = new _02_MissingParentheses.Ex4();
    var init = List.of("a", "b", "c");

    assertEquals(1, target.before(init, null));
    assertEquals(1, target.before(init, "d"));

    assertEquals(3, target.after(init, null));
    assertEquals(4, target.after(init, "d"));
  }
}
