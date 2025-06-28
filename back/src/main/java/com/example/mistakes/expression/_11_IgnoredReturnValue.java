package com.example.mistakes.expression;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.CheckReturnValue;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _11_IgnoredReturnValue {

  _11_IgnoredReturnValue(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class, Ex2.class, Ex3.class,
        Ex4.class, Ex5.class, Ex6.class, Ex7.class);
    service.addAll(entities);
  }

  static class Ex1 {
    String before() {
      String s = "Hello World!";
      s.substring(6);
      return s;
    }

    String after() {
      String s = "Hello World!";
      return s.substring(6);
    }
  }

  static class Ex2 {
    BigInteger before() {
      var value = BigInteger.TWO;
      value.add(BigInteger.ONE);
      return value;
    }

    BigInteger after() {
      var value = BigInteger.TWO;
      return value.add(BigInteger.ONE);
    }
  }

  static class Ex3 {
    void before(List<String> list) {
      list.forEach(String::trim);
    }

    void after(List<String> list) {
      list.replaceAll(String::trim);
    }
  }

  static class Ex4 {
    // instead of executing internal processing, return the target bitset: s1
    BitSet before(BitSet s1, BitSet s2) {
      s1.intersects(s2);
      return s1;
    }

    BitSet after(BitSet s1, BitSet s2) {
      s1.and(s2);
      return s1;
    }
  }

  static class Ex5 {
    void before() {
      List<String> list = List.of();
      assertThat(!list.isEmpty());
    }

    void after() {
      List<String> list = List.of();
      assertThat(!list.isEmpty()).isTrue();
    }

    private record Assert(boolean condition) {
      void isTrue() {
        if (!condition) {
          throw new AssertionError("Condition is not true");
        }
      }
    }

    private Assert assertThat(boolean condition) {
      return new Assert(condition);
    }
  }

  static class Ex6 {
    List<String> before(List<String> stringList) {
      stringList.stream().sorted();
      stringList.stream().filter(str -> !str.isEmpty());
      return stringList;
    }

    @CheckReturnValue
    List<String> after(List<String> stringList) {
      // if given stringList is mutable
      if (stringList instanceof ArrayList) {
        stringList.removeIf(String::isEmpty);
        stringList.sort(null);
        return stringList;
      }

      stringList = stringList.stream().filter(str -> !str.isEmpty()).toList();
      stringList = stringList.stream().sorted().toList();
      return stringList;
    }
  }

  static class Ex7 {
    // instead of process(), return the target byte array: arr
    byte[] before(InputStream is) throws IOException {
      byte[] arr = new byte[100];
      is.read(arr);
      return arr;
    }

    byte[] after(InputStream is) {
      byte[] arr = new byte[100];
      try {
        arr = is.readNBytes(100);
      } catch (IOException _) {
        return new byte[0];
      }
      return arr;
    }
  }

}