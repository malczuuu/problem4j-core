package io.github.malczuuu.problem4j.core;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProblemStatusTests {

  @ParameterizedTest
  @ValueSource(ints = {103, 413, 414})
  void givenAmbiguousStatusCode_shouldPrioritizeNonDeprecatedOne(int value)
      throws NoSuchFieldException {

    Optional<ProblemStatus> optionalStatus = ProblemStatus.findValue(value);

    assertTrue(optionalStatus.isPresent());
    ProblemStatus status = optionalStatus.get();
    Deprecated deprecationNotice =
        status.getClass().getField(status.name()).getAnnotation(Deprecated.class);
    assertNull(deprecationNotice);
  }
}
