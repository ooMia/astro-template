package com.example.mistakes.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mistakes.QuestionEntityBuilder;
import com.example.mistakes.service.QuestionService;

@Component
public class _04_MultilineStringLiteral {

  _04_MultilineStringLiteral(@Autowired QuestionService service) {
    var entities = QuestionEntityBuilder.of(Ex1.class);
    service.add(entities);
  }

  static class Ex1 {
    String before(String userName) {
      // @formatter:off
      String greetingPage = "<html>\n"
          + "  <head><title>Welcome</title></head>\n"
          + "  <body>\n"
          + "    <h1>Hello, $user$!</h1>\n"
          + "  <hr>\n"
          + "  <p>Welcome to our web-site</p>\n"
          + "  </body>\n"
          + "</html>\n".replace("$user$", userName);
      // @formatter:on
      return greetingPage;
    }

    String after(String userName) {
      String greetingPage = """
          <html>
            <head><title>Welcome</title></head>
            <body>
              <h1>Hello, $user$!</h1>
            <hr>
            <p>Welcome to our web-site</p>
            </body>
          </html>
          """.replace("$user$", userName);
      return greetingPage;
    }
  }
}
