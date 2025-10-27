package io.github.malczuuu.problem4j.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
  void givenProblemStatus_shouldSetNumericStatusAndTitle() {
    Problem problem = Problem.builder().status(ProblemStatus.BAD_REQUEST).build();

    assertThat(problem.getStatus()).isEqualTo(ProblemStatus.BAD_REQUEST.getStatus());
    assertThat(problem.getTitle()).isEqualTo(ProblemStatus.BAD_REQUEST.getTitle());
  }

  @Test
  void givenProblemStatus_shouldPreferExplicitStatusValueWhenSetEarlier() {
    Problem problem = Problem.builder().status(405).status(ProblemStatus.I_AM_A_TEAPOT).build();

    assertThat(problem.getStatus()).isEqualTo(ProblemStatus.I_AM_A_TEAPOT.getStatus());
    assertThat(problem.getTitle()).isEqualTo(ProblemStatus.I_AM_A_TEAPOT.getTitle());
  }

  @Test
  void givenExplicitTitle_thenStatusProblemStatus_shouldNotOverrideTitle() {
    Problem problem =
        Problem.builder().title("Custom Title").status(ProblemStatus.BAD_REQUEST).build();

    assertThat(problem.getStatus()).isEqualTo(ProblemStatus.BAD_REQUEST.getStatus());
    assertThat(problem.getTitle()).isEqualTo("Custom Title");
  }

  @Test
  void givenStatusProblemStatus_thenExplicitTitle_shouldOverrideDerivedTitle() {
    Problem problem = Problem.builder().status(ProblemStatus.NOT_FOUND).title("My Title").build();

    assertThat(problem.getStatus()).isEqualTo(ProblemStatus.NOT_FOUND.getStatus());
    assertThat(problem.getTitle()).isEqualTo("My Title");
  }

  @Test
  void givenInvalidTypeString_shouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> Problem.builder().type("ht tp://not a uri"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void givenInvalidInstanceString_shouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> Problem.builder().instance("::://invalid"))
        .isInstanceOf(IllegalArgumentException.class);
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

  @Test
  void numericStatus_shouldDeriveTitleWhenKnown() {
    Problem problem = Problem.builder().status(ProblemStatus.MULTI_STATUS.getStatus()).build();

    assertThat(problem.getStatus()).isEqualTo(ProblemStatus.MULTI_STATUS.getStatus());
    assertThat(problem.getTitle()).isEqualTo(ProblemStatus.MULTI_STATUS.getTitle());
  }

  @Test
  void unknownNumericStatus_shouldNotDeriveTitle() {
    Problem problem = Problem.builder().status(999).build();

    assertThat(problem.getStatus()).isEqualTo(999);
    assertThat(problem.getTitle()).isNull();
  }

  @Test
  void typeAndInstance_stringOverloads_shouldAcceptValidUris() {
    String t = "http://example.org/type";
    String i = "http://example.org/instance";

    Problem problem = Problem.builder().type(t).instance(i).build();

    assertThat(problem.getType()).isEqualTo(URI.create(t));
    assertThat(problem.getInstance()).isEqualTo(URI.create(i));
  }

  @Test
  void emptyMapExtension_shouldBeIgnored() {
    Map<String, Object> m = new HashMap<>();
    Problem problem = Problem.builder().extension(m).build();

    assertThat(problem.getExtensions()).isEmpty();
  }

  @Test
  void nullValuedExtension_isPresentButOmittedFromToString() {
    Problem problem = Problem.builder().extension("ext", null).build();

    assertThat(problem.getExtensions()).containsExactly("ext");
    assertThat(problem.getExtensionValue("ext")).isNull();

    String s = problem.toString();
    assertThat(s).doesNotContain("\"ext\"");
  }

  @Test
  void laterExtensions_shouldOverwriteEarlierValues() {
    Problem problem =
        Problem.builder().extension("k", "v1").extension(Problem.extension("k", "v2")).build();

    assertThat(problem.getExtensionValue("k")).isEqualTo("v2");
  }
}
