package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.junit.jupiter.api.Test;

class _11_IgnoredReturnValueTests {

  @Test
  void testEx1() {
    var target = new _11_IgnoredReturnValue.Ex1();

    assertEquals("Hello World!", target.before());
    assertEquals("World!", target.after());
  }

  @Test
  void testEx2() {
    var target = new _11_IgnoredReturnValue.Ex2();

    assertEquals(BigInteger.TWO, target.before());
    assertEquals(BigInteger.valueOf(3), target.after());
  }

  @Test
  void testEx3() {
    var target = new _11_IgnoredReturnValue.Ex3();
    var list = new ArrayList<String>(List.of("  Hello  ", "  World  "));

    target.before(list);
    assertEquals(List.of("  Hello  ", "  World  "), list);

    target.after(list);
    assertEquals(List.of("Hello", "World"), list);
  }

  @Test
  void testEx4() {
    var target = new _11_IgnoredReturnValue.Ex4();
    var s1 = new BitSet();
    var s2 = new BitSet();

    s1.set(0, 8);
    s2.set(1);
    s2.set(3);
    s2.set(5);
    s2.set(7);

    System.out.println(s1); // 1111_1111
    System.out.println(s2); // 1010_1010

    assertEquals(s1, target.before(s1, s2));
    assertEquals(s2, target.after(s1, s2));
  }

  @Test
  void testEx5() {
    var target = new _11_IgnoredReturnValue.Ex5();

    assertDoesNotThrow(target::before);
    assertThrows(AssertionError.class, target::after);
  }

  @Test
  void testEx6() {
    var target = new _11_IgnoredReturnValue.Ex6();
    List<String> list = List.of("", "World", "Hello", "");
    List<String> expect = List.of("Hello", "World");

    assertEquals(list, target.before(list));
    assertEquals(expect, target.after(list));
  }

  @Test
  void testEx7() {
    var target = new _11_IgnoredReturnValue.Ex7();

    String str = "Hello, World!";
    byte[] content = str.getBytes();

    try {
      {
        var stream = new BufferedInputStream(new ByteArrayInputStream(content));
        assertEquals(str, new String(target.before(stream)).trim());
        stream.close();
        assertThrows(IOException.class, () -> target.before(stream));
      }
      {
        var stream = new BufferedInputStream(new ByteArrayInputStream(content));
        assertEquals(str, new String(target.after(stream)));
        stream.close();
        assertEquals("", new String(target.after(stream)));
      }
    } catch (IOException _) {
      throw new AssertionError("IOException occurred");
    }
  }
}
