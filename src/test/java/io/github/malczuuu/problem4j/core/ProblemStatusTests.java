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
package io.github.malczuuu.problem4j.core;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProblemStatusTests {

  @ParameterizedTest
  @ValueSource(ints = {103, 413, 414, 416, 422})
  void givenAmbiguousStatusCode_shouldPrioritizeNonDeprecatedOne(int value)
      throws NoSuchFieldException {

    Optional<ProblemStatus> optionalStatus = ProblemStatus.findValue(value);

    assertThat(optionalStatus.isPresent()).isTrue();
    ProblemStatus status = optionalStatus.get();
    Deprecated deprecationNotice =
        status.getClass().getField(status.name()).getAnnotation(Deprecated.class);
    assertThat(deprecationNotice).isNull();

    List<ProblemStatus> candidates =
        Stream.of(ProblemStatus.values()).filter(v -> v.getStatus() == value).collect(toList());
    assertThat(candidates.size())
        .withFailMessage("there was exactly 1 candidate for " + value)
        .isGreaterThan(1);
  }

  @ParameterizedTest
  @ValueSource(ints = {305})
  void givenSingleDeprecatedStatusCode_shouldKeepIt(int value) throws NoSuchFieldException {
    Optional<ProblemStatus> optionalStatus = ProblemStatus.findValue(value);

    assertThat(optionalStatus.isPresent()).isTrue();
    ProblemStatus status = optionalStatus.get();
    Deprecated deprecationNotice =
        status.getClass().getField(status.name()).getAnnotation(Deprecated.class);
    assertThat(deprecationNotice).isNotNull();

    List<ProblemStatus> candidates =
        Stream.of(ProblemStatus.values()).filter(v -> v.getStatus() == value).collect(toList());
    assertThat(candidates.size())
        .withFailMessage("there were more than 1 candidates for " + value)
        .isEqualTo(1);
  }
}
