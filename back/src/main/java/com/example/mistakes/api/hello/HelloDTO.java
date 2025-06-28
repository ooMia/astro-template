package com.example.mistakes.api.hello;

import com.example.mistakes.base.type.Message;

@Deprecated
public record HelloDTO(String message) implements Message {}
