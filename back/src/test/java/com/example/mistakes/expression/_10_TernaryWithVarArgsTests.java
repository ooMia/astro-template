package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.MissingFormatArgumentException;

import org.junit.jupiter.api.Test;

class _10_TernaryWithVarArgsTests {

  @Test
  void testEx1() {
    var target = new _10_TernaryWithVarArgs.Ex1();

    var patternSingle = "User: %s";
    var expectSingle = "User: Alice";
    var userSingle = new Object[] { "Alice" };

    var patternDuo = "User: %s %s";
    var expectDuo = "User: Alice Bob";
    var userDuo = new Object[] { "Alice", "Bob" };

    assertNotEquals(expectSingle, target.before(patternSingle, userSingle));
    assertEquals(expectSingle, target.after(patternSingle, userSingle));

    assertThrows(MissingFormatArgumentException.class,
        () -> target.before(patternDuo, userDuo));
    assertEquals(expectDuo, target.after(patternDuo, userDuo));
  }

}