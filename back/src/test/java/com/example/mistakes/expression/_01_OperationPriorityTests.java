package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class _01_OperationPriorityTests {

  @ParameterizedTest
  @MethodSource("dualCombinations")
  void testEx1(short lo, short hi) {
    var target = new _01_OperationPriority.Ex1();
    int expected = lo * (int) Math.pow(2, 16) + hi;
    assertEquals(expected, target.after(lo, hi), "Test failed with lo=%d hi=%d".formatted(lo, hi));
  }

  static Stream<Arguments> dualCombinations() {
    var values = List.of((short) 0, (short) 1, (short) 2, Short.MAX_VALUE);
    return values.stream().flatMap(lo -> values.stream().map(hi -> Arguments.of(lo, hi)));
  }

  @Test
  void testEx2() {
    var target = new _01_OperationPriority.Ex2();

    printBinary(1);
    printBinary(1 + 1);
    printBinary(1 + 1 << 8);
    printBinary(1 + 1 << 8 + 1);
    printBinary(1 + 1 << 8 + 1 << 16);
    printBinary(1 + 1 << 8 + 1 << 16 + 1);
    printBinary(1 + 1 << 8 + 1 << 16 + 1 << 24);
    printBinary(target.after());

    assertEquals(0, target.before());
  }

  private static void printBinary(int value) {
    printBinary(value, 32);
  }

  private static void printBinary(int value, int length) {
    System.out.println(String.format("%" + length + "s", Integer.toBinaryString(value)).replace(' ', '0'));
  }

  @Test
  void testEx3() {
    var target = new _01_OperationPriority.Ex3();

    assertEquals(target.BLOCK_SIZE * 0.5, target.before());
    assertEquals(target.BLOCK_SIZE * 1.25, target.after());
  }

  @Test
  void testEx4() {
    var target = new _01_OperationPriority.Ex4();
    var input = 0x0FF0;

    printBinary(input, 16);
    printBinary(target.before(input), 16);
    printBinary(target.after(input), 16);

    assertEquals(input % 2, target.before(input) % 2);
    assertEquals(1, target.after(input) % 2);
  }
}
