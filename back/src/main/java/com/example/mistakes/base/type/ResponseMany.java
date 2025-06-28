package com.example.mistakes.base.type;

public interface ResponseMany<T> {
  Iterable<T> result();

  Number length();
}
