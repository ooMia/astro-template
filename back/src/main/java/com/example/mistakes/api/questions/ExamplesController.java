package com.example.mistakes.api.questions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mistakes.base.type.ResponseMany;
import com.example.mistakes.service.QuestionService;

@RestController
@RequestMapping("/api/examples")
public class ExamplesController {
  private final QuestionService service;

  public ExamplesController(QuestionService service) {
    this.service = service;
  }

  private ResponseEntity<ResponseMany<QuestionEntity>> _response(
      Iterable<QuestionEntity> data) {
    final var dto = new ResClassQuestion(data);
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping("/")
  public ResponseEntity<ResponseMany<QuestionEntity>> getAll() {
    final var data = this.service.findAll();
    return _response(data);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseMany<QuestionEntity>> getOneById(
      @PathVariable("id") int id) {
    final var data = this.service.findAllByMistakeId(id);
    return _response(data);
  }
}
