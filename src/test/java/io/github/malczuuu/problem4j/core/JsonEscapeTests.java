package io.github.malczuuu.problem4j.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JsonEscapeTests {

  @ParameterizedTest
  @CsvSource({
    "'\"','\\\"','quote'",
    "'\\','\\\\','backslash'",
    "'\b','\\b','backspace'",
    "'\f','\\f','formfeed'",
    "'\n','\\n','newline'",
    "'\r','\\r','carriage return'",
    "'\t','\\t','tab'",
    "'/','\\/','slash'",
  })
  void shouldEscapeSpecialCharactersWithSlash(String given, String expected, String name) {
    String result = JsonEscape.escape(given);

    assertThat(result)
        .withFailMessage(name + " (\"" + expected + "\") failed with \"" + result + "\"")
        .isEqualTo(expected);
  }
}
