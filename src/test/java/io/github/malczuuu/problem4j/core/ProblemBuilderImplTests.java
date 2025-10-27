package io.github.malczuuu.problem4j.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ProblemBuilderImplTests {

  @Test
  void givenNullURIType_shouldNotSetIt() {
    Problem problem = Problem.builder().type((URI) null).build();

    assertThat(problem.getType()).isEqualTo(Problem.BLANK_TYPE);
  }

  @Test
  void givenNullStringType_shouldNotSetIt() {
    Problem problem = Problem.builder().type((String) null).build();

    assertThat(problem.getType()).isEqualTo(Problem.BLANK_TYPE);
  }

  @Test
  void givenNullProblemStatus_shouldNotSetTitleOrStatus() {
    Problem problem = Problem.builder().status(null).build();

    assertThat(problem.getStatus()).isZero();
    assertThat(problem.getTitle()).isNull();
  }

  @Test
  void givenNullURIInstance_shouldNotSetIt() {
    Problem problem = Problem.builder().instance((URI) null).build();

    assertThat(problem.getInstance()).isNull();
  }

  @Test
  void givenNullStringInstance_shouldNotSetIt() {
    Problem problem = Problem.builder().instance((String) null).build();

    assertThat(problem.getInstance()).isNull();
  }

  @Test
  void givenNullNameExtension_shouldIgnoreIt() {
    Problem problem = Problem.builder().extension(null, "value").build();

    assertThat(problem.getExtensions()).isEmpty();
  }

  @Test
  void givenNullMapExtension_shouldIgnoreIt() {
    Problem problem = Problem.builder().extension((Map<String, Object>) null).build();

    assertThat(problem.getExtensions()).isEmpty();
  }

  @Test
  void givenMapExtensionWithNullKey_shouldIgnoreNullKey() {
    Map<String, Object> map = new HashMap<>();
    map.put(null, "ignored");
    map.put("a", "b");

    Problem problem = Problem.builder().extension(map).build();

    assertThat(problem.getExtensions()).containsExactly("a");
    assertThat(problem.getExtensionValue("a")).isEqualTo("b");
  }

  @Test
  void givenNullVarargArray_shouldIgnoreIt() {
    Problem problem = Problem.builder().extension((Problem.Extension[]) null).build();

    assertThat(problem.getExtensions()).isEmpty();
  }

  @Test
  void givenVarargWithNullElement_shouldIgnoreNullElement() {
    Problem problem =
        Problem.builder()
            .extension(Problem.extension("a", 1), null, Problem.extension("b", 2))
            .build();

    assertThat(problem.getExtensions()).containsExactlyInAnyOrder("a", "b");
  }

  @Test
  void givenNullCollection_shouldIgnoreIt() {
    Problem problem = Problem.builder().extension((Collection<Problem.Extension>) null).build();

    assertThat(problem.getExtensions()).isEmpty();
  }

  @Test
  void givenCollectionWithNullElement_shouldIgnoreNullElement() {
    Problem problem =
        Problem.builder()
            .extension(
                Arrays.asList(Problem.extension("x", "1"), null, Problem.extension("y", "2")))
            .build();

    assertThat(problem.getExtensions()).containsExactlyInAnyOrder("x", "y");
  }
}
