package com.example.mistakes.api.hello;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@RequestMapping("/api")
public class HelloController {

  @GetMapping("/hello")
  public ResponseEntity<HelloDTO> hello() {
    final var dto = new HelloDTO("Hello, World!");
    return ResponseEntity.ok().body(dto);
  }
}
