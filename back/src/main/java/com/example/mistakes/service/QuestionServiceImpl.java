package com.example.mistakes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mistakes.api.questions.QuestionEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  @Autowired
  private final ReadOnlyFsRepository<QuestionEntity, String> repository;

  Map<String, Integer> chpaterMap = new HashMap<>(Map.of("expression", 2));

  private int _convertChapterNameToNumber(String name)
      throws NoSuchElementException {
    if (!chpaterMap.containsKey(name)) {
      throw new NoSuchElementException("Chapter not found: %s".formatted(name));
    }
    return chpaterMap.get(name);
  }

  @Override
  public void add(QuestionEntity entity) {
    repository.save(entity);
  }

  @Override
  public void addAll(Iterable<QuestionEntity> entity) {
    repository.saveAll(entity);
  }

  @Override
  public QuestionEntity findOne(String id) {
    return repository.findById(id).orElseThrow();
  }

  @Override
  public QuestionEntity findOne(String chapterName, int mistakeId,
      int exampleId) {
    var chapterNumber = _convertChapterNameToNumber(chapterName);
    return findOne(chapterNumber, mistakeId, exampleId);
  }

  @Override
  public QuestionEntity findOne(int chapterNumber, int mistakeId,
      int exampleId) {
    var id = "%d_%02d_%d".formatted(chapterNumber, mistakeId, exampleId);
    return findOne(id);
  }

  @Override
  public List<QuestionEntity> findAll() {
    return (List<QuestionEntity>) repository.findAll();
  }

  @Override
  public List<QuestionEntity> findAllByChapterName(String name) {
    var chapterNumber = _convertChapterNameToNumber(name);
    return findAllByChapterNumber(chapterNumber);
  }

  @Override
  public List<QuestionEntity> findAllByChapterNumber(Number number) {
    var regex = "%d_\\d+_\\d+".formatted(number);
    return _findAllByPattern(regex);
  }

  @Override
  public List<QuestionEntity> findAllByMistakeId(Number id) {
    var regex = "\\d+_%02d_\\d+".formatted(id);
    return _findAllByPattern(regex);
  }

  private List<QuestionEntity> _findAllByPattern(String regex) {
    return (List<QuestionEntity>) repository.findAllByPattern(regex);
  }
}
