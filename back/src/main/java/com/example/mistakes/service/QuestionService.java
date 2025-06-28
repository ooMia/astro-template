package com.example.mistakes.service;

import java.util.List;

import com.example.mistakes.api.questions.QuestionEntity;

public interface QuestionService {
  // Register
  void add(QuestionEntity entity);

  void addAll(Iterable<QuestionEntity> entity);

  // find one
  QuestionEntity findOne(String id);

  QuestionEntity findOne(String chapterName, int mistakeId, int exampleId);

  QuestionEntity findOne(int chapterNumber, int mistakeId, int exampleId);

  // find many
  List<QuestionEntity> findAll();

  List<QuestionEntity> findAllByChapterName(String name);

  List<QuestionEntity> findAllByChapterNumber(Number number);

  List<QuestionEntity> findAllByMistakeId(Number id);
}
