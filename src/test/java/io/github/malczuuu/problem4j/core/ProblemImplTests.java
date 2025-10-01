package io.github.malczuuu.problem4j.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ProblemImplTests {

  @Test
  void givenAllFieldsPopulated_whenToString_thenContainsAllFieldsProperly() {
    URI type = URI.create("https://example.com/problem");
    String title = "Test Problem";
    int status = 400;
    String detail = "Something went wrong";
    URI instance = URI.create("https://example.com/instance");
    Map<String, Object> extensions = new HashMap<>();
    extensions.put("stringExt", "value");
    extensions.put("numberExt", 42);
    extensions.put("booleanExt", true);
    extensions.put("objectExt", new DummyObject("foo"));

    ProblemImpl problem = new ProblemImpl(type, title, status, detail, instance, extensions);

    String result = problem.toString();

    assertThat(result).contains("\"type\" : \"" + JsonEscape.escape(type.toString()) + "\"");
    assertThat(result).contains("\"title\" : \"" + JsonEscape.escape(title) + "\"");
    assertThat(result).contains("\"status\" : " + status);
    assertThat(result).contains("\"detail\" : \"" + JsonEscape.escape(detail) + "\"");
    assertThat(result)
        .contains("\"instance\" : \"" + JsonEscape.escape(instance.toString()) + "\"");
    assertThat(result).contains("\"stringExt\" : \"" + JsonEscape.escape("value") + "\"");
    assertThat(result).contains("\"numberExt\" : 42");
    assertThat(result).contains("\"booleanExt\" : true");
    assertThat(result).contains("\"objectExt\" : \"DummyObject:" + JsonEscape.escape("foo") + "\"");
  }

  @Test
  void givenNullExtensionsAndNullableFields_whenToString_thenOmitsNulls() {
    Map<String, Object> extensions = new HashMap<>();
    ProblemImpl problem = new ProblemImpl(null, null, 200, null, null, extensions);

    String result = problem.toString();

    assertThat(result).isEqualTo("{ \"status\" : 200 }");
  }

  @Test
  void givenOnlyStringExtensions_whenToString_thenContainsQuotedStrings() {
    Map<String, Object> extensions = new HashMap<>();
    extensions.put("ext1", "value1");
    extensions.put("ext2", "value2");

    ProblemImpl problem = new ProblemImpl(null, null, 0, null, null, extensions);

    String result = problem.toString();

    assertThat(result).contains("\"ext1\" : \"" + JsonEscape.escape("value1") + "\"");
    assertThat(result).contains("\"ext2\" : \"" + JsonEscape.escape("value2") + "\"");
  }

  @Test
  void givenOnlyNumberExtensions_whenToString_thenContainsNumbers() {
    Map<String, Object> extensions = new HashMap<>();
    extensions.put("ext1", 123);
    extensions.put("ext2", 456.78);

    ProblemImpl problem = new ProblemImpl(null, null, 0, null, null, extensions);

    String result = problem.toString();

    assertThat(result).contains("\"ext1\" : 123");
    assertThat(result).contains("\"ext2\" : 456.78");
  }

  @Test
  void givenOnlyBooleanExtensions_whenToString_thenContainsBooleans() {
    Map<String, Object> extensions = new HashMap<>();
    extensions.put("flag1", true);
    extensions.put("flag2", false);

    ProblemImpl problem = new ProblemImpl(null, null, 0, null, null, extensions);

    String result = problem.toString();

    assertThat(result).contains("\"flag1\" : true");
    assertThat(result).contains("\"flag2\" : false");
  }

  @Test
  void givenNonPrimitiveExtension_whenToString_thenUsesClassNamePrefix() {
    Map<String, Object> extensions = new HashMap<>();
    extensions.put("obj", new DummyObject("bar"));

    ProblemImpl problem = new ProblemImpl(null, null, 0, null, null, extensions);

    String result = problem.toString();

    assertThat(result).contains("\"obj\" : \"DummyObject:" + JsonEscape.escape("bar") + "\"");
  }

  @Test
  void givenStringWithSpecialCharacters_whenToString_thenProperlyEscapes() {
    Map<String, Object> extensions = new HashMap<>();
    extensions.put("ext", "a\"b\\c\nd");

    ProblemImpl problem = new ProblemImpl(null, null, 0, null, null, extensions);

    String result = problem.toString();

    assertThat(result).contains("\"ext\" : \"" + JsonEscape.escape("a\"b\\c\nd") + "\"");
  }

  private static class DummyObject {
    private final String value;

    DummyObject(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
