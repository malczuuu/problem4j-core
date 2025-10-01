package io.github.malczuuu.problem4j.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.URI;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ProblemTests {

  @Test
  void givenProblemStatus_shouldSetTitleAndStatus() {
    Problem problem = Problem.builder().status(ProblemStatus.MULTI_STATUS).build();

    assertThat(problem.getTitle()).isEqualTo(ProblemStatus.MULTI_STATUS.getTitle());
    assertThat(problem.getStatus()).isEqualTo(ProblemStatus.MULTI_STATUS.getStatus());
  }

  @Test
  void givenProblem_whenToBuilder_shouldBeAbleToRecreateOriginal() {
    Problem problem =
        Problem.builder()
            .type(URI.create("http://example.org/problem1"))
            .title("Problem1")
            .status(400)
            .detail("this is problem1")
            .instance(URI.create("http://example.org/instance1"))
            .extension("extCode1", "extValue1")
            .extension(Problem.extension("extCode2", "extValue2"))
            .extension(
                Arrays.asList(
                    Problem.extension("extCode3", "extValue3"),
                    Problem.extension("extCode4", "extValue4")))
            .extension(
                Stream.of(
                        Problem.extension("extCode5", "extValue5"),
                        Problem.extension("extCode6", "extValue6"),
                        Problem.extension("extCode7", "extValue7"))
                    .collect(Collectors.toMap(Problem.Extension::getKey, Function.identity())))
            .build();

    ProblemBuilder builder = problem.toBuilder();
    Problem copy = builder.build();

    assertThat(problem).isNotSameAs(copy);
    assertThat(problem).isEqualTo(copy);
  }

  @Test
  void givenProblemExtensionWithNullKey_shouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> Problem.extension(null, "v"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("key cannot be null");
  }

  @Test
  void givenProblemExtensionWithKey_shouldCreateExtension() {
    Problem.Extension ext = Problem.extension("code", 123);

    assertThat(ext.getKey()).isEqualTo("code");
    assertThat(ext.getValue()).isEqualTo(123);
  }
}
