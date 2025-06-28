package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class _13_IncorrectMethodBindingTests {

  @Test
  void testEx1() {
    var target = new _13_IncorrectMethodBinding.Ex1();
    {
      var storage = target.new Storage();
      var expect = """
          File name: numbers.txt
          Content:
          numbers.txt3.1415

          File name: users.txt
          Content:
          users.txtadmin
          guest

          """;
      assertEquals(expect, target.before(storage));
    }
    {
      var expect = """
          File name: numbers.txt
          Content:
          3.1415

          File name: users.txt
          Content:
          admin
          guest

          """;
      assertEquals(expect, target.after());
    }
  }

  @Test
  void testEx2() {
    var target = new _13_IncorrectMethodBinding.Ex2();

    assertEquals(2, target.before("k", "v").size());
    assertEquals(1, target.after("k", "v").size());
  }

  @Test
  void testEx3() {
    var target = new _13_IncorrectMethodBinding.Ex3();
    {
      var arr = target.before(100);
      assertEquals(99, arr[99].capacity());
    }
    {
      var arr = target.after(100);
      assertEquals(16, arr[99].capacity());
    }
  }
}
