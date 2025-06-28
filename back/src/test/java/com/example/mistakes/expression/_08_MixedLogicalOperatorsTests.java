package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

class _08_MixedLogicalOperatorsTests {
  @Test
  void testEx1() {
    var target = new _08_MixedLogicalOperators.Ex1();

    assertDoesNotThrow(() -> target.before(-1));
    assertThrows(IllegalArgumentException.class, () -> target.after(-1));
  }

  @Test
  void testEx2() {
    var target = new _08_MixedLogicalOperators.Ex2();
    byte[] invalid = new byte[] { 0x19, 0x7F };

    assertTrue(target.before(invalid));
    assertFalse(target.after(invalid));
  }

  @Test
  void testEx3() {
    var target = new _08_MixedLogicalOperators.Ex3();

    var randomString = _generateRandomAsciiString(3);
    assertTrue(target.before(randomString));

    assertTrue(target.after(""));
    assertFalse(target.after("//"));
    assertFalse(target.after("#"));
  }

  @Test
  void testEx4() {
    var target = new _08_MixedLogicalOperators.Ex4();
    var rand = new Random().nextInt();

    assertDoesNotThrow(() -> target.before(rand));
    assertThrows(IllegalArgumentException.class, () -> target.after(9));
    assertThrows(IllegalArgumentException.class, () -> target.after(21));
  }

  private String _generateRandomAsciiString(int length) {
    var charSet = new StringBuilder();
    for (char c = 0x20; c <= 0x7E; c++) {
      charSet.append(c);
    }
    charSet.append('\n').append('\t');

    var random = new Random();
    var builder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      var index = random.nextInt(charSet.length());
      builder.append(charSet.charAt(index));
    }
    return builder.toString();
  }
}