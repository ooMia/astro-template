package com.example.mistakes.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class _04_MultilineStringLiteralTests {
  @Test
  void testEx1() {
    var target = new _04_MultilineStringLiteral.Ex1();

    String template = """
        <html>
          <head><title>Welcome</title></head>
          <body>
            <h1>Hello, $user$!</h1>
          <hr>
          <p>Welcome to our web-site</p>
          </body>
        </html>
        """;
    String user = "Tom";
    String expect = template.replace("$user$", user);

    assertEquals(template, target.before(user));
    assertEquals(expect, target.after(user));
  }
}