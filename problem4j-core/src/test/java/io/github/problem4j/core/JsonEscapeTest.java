/*
 * Copyright (c) 2025 Damian Malczewski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * SPDX-License-Identifier: MIT
 */
package io.github.problem4j.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JsonEscapeTest {

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
