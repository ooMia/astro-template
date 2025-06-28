package com.example.mistakes.expression;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _14_WrongMethodReference {
  _14_WrongMethodReference(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class);
    service.add(entities);
  }

  static class Ex1 {
    List<Integer> before() {
      List<Integer> list = Arrays.asList(0, -3, -2, 3, -1, 1, 2);
      list.sort(Integer::max);
      return list;
    }

    List<Integer> after() {
      List<Integer> list = Arrays.asList(0, -3, -2, 3, -1, 1, 2);
      list.sort((a, b) -> Integer.max(a, b) == a ? 1 : -1);
      return list;
    }
  }
}
