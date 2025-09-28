package io.github.malczuuu.problem4j.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    assertEquals(expected, result, name + " (\"" + expected + "\") failed with \"" + result + "\"");
  }
}
