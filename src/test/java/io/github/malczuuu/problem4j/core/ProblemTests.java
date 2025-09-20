package io.github.malczuuu.problem4j.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ProblemTests {

  @Test
  void givenEvenNumberOfArguments_whenCreatingProblem_thenExtensionsBuiltCorrectly() {
    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            "key1",
            "value1",
            "key2",
            "value2");

    assertEquals(2, problem.getExtensions().size());
    assertEquals("value1", problem.getExtensionValue("key1"));
    assertEquals("value2", problem.getExtensionValue("key2"));
  }

  @Test
  void givenOddNumberOfArguments_whenCreatingProblem_thenFailWithException() {
    IllegalArgumentException e =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new Problem(
                  URI.create("https://example.com/error"),
                  "Test Error",
                  400,
                  "Test detail",
                  URI.create("https://example.com/instance"),
                  "key1",
                  "value1",
                  "key2");
            });

    assertEquals("extensions arguments must be key-value pairs", e.getMessage());
  }

  @Test
  void givenEmptyArgumentsArray_whenCreatingProblem_thenNoExtensions() {
    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"));

    assertTrue(problem.getExtensions().isEmpty());
  }

  @Test
  void givenNullKeyInArguments_whenCreatingProblem_thenNullPointerExceptionThrown() {
    assertThrows(
        NullPointerException.class,
        () -> {
          new Problem(
              URI.create("https://example.com/error"),
              "Test Error",
              400,
              "Test detail",
              URI.create("https://example.com/instance"),
              null,
              "value1",
              "key2",
              "value2");
        });
  }

  @Test
  void givenNullValueInArguments_whenCreatingProblem_thenNullPointerExceptionThrown() {
    assertThrows(
        NullPointerException.class,
        () -> {
          new Problem(
              URI.create("https://example.com/error"),
              "Test Error",
              400,
              "Test detail",
              URI.create("https://example.com/instance"),
              "key1",
              null,
              "key2",
              "value2");
        });
  }

  @Test
  void givenNumericKeys_whenCreatingProblem_thenKeysConvertedToStrings() {
    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            123,
            "numericKey",
            456.78,
            "floatKey");

    assertEquals(2, problem.getExtensions().size());
    assertEquals("numericKey", problem.getExtensionValue("123"));
    assertEquals("floatKey", problem.getExtensionValue("456.78"));
  }

  @Test
  void givenComplexObjectValues_whenCreatingProblem_thenObjectsStoredAsValues() {
    Object complexObject =
        new Object() {
          @Override
          public String toString() {
            return "complex";
          }
        };

    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            "objectKey",
            complexObject,
            "booleanKey",
            true);

    assertEquals(2, problem.getExtensions().size());
    assertEquals(complexObject, problem.getExtensionValue("objectKey"));
    assertEquals(true, problem.getExtensionValue("booleanKey"));
  }

  @Test
  void givenLargeNumberOfArguments_whenCreatingProblem_thenAllPairsProcessed() {
    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            "k1",
            "v1",
            "k2",
            "v2",
            "k3",
            "v3",
            "k4",
            "v4",
            "k5",
            "v5");

    assertEquals(5, problem.getExtensions().size());
    assertEquals("v1", problem.getExtensionValue("k1"));
    assertEquals("v2", problem.getExtensionValue("k2"));
    assertEquals("v3", problem.getExtensionValue("k3"));
    assertEquals("v4", problem.getExtensionValue("k4"));
    assertEquals("v5", problem.getExtensionValue("k5"));
  }

  @Test
  void givenDuplicateKeys_whenCreatingProblem_thenLastValueWins() {
    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            "duplicateKey",
            "firstValue",
            "duplicateKey",
            "secondValue");

    assertEquals(1, problem.getExtensions().size());
    assertEquals("secondValue", problem.getExtensionValue("duplicateKey"));
  }

  @Test
  void givenExtensionArrayConstructor_whenCreatingProblem_thenExtensionsBuiltFromArray() {
    Problem.Extension ext1 = Problem.extension("key1", "value1");
    Problem.Extension ext2 = Problem.extension("key2", "value2");

    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            ext1,
            ext2);

    assertEquals(2, problem.getExtensions().size());
    assertEquals("value1", problem.getExtensionValue("key1"));
    assertEquals("value2", problem.getExtensionValue("key2"));
  }

  @Test
  void givenSetExtensionConstructor_whenCreatingProblem_thenExtensionsBuiltFromSet() {
    java.util.Set<Problem.Extension> extensions =
        java.util.Set.of(Problem.extension("key1", "value1"), Problem.extension("key2", "value2"));

    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            extensions);

    assertEquals(2, problem.getExtensions().size());
    assertEquals("value1", problem.getExtensionValue("key1"));
    assertEquals("value2", problem.getExtensionValue("key2"));
  }

  @Test
  void givenEmptyExtensionArray_whenCreatingProblem_thenNoExtensions() {
    Problem.Extension[] emptyExtensions = new Problem.Extension[0];

    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            emptyExtensions);

    assertTrue(problem.getExtensions().isEmpty());
  }

  @Test
  void givenEmptyExtensionSet_whenCreatingProblem_thenNoExtensions() {
    java.util.Set<Problem.Extension> emptyExtensions = java.util.Set.of();

    Problem problem =
        new Problem(
            URI.create("https://example.com/error"),
            "Test Error",
            400,
            "Test detail",
            URI.create("https://example.com/instance"),
            emptyExtensions);

    assertTrue(problem.getExtensions().isEmpty());
  }

  @Test
  void givenExtensionsWithNullValues_whenCreatingProblem_thenNullPointerExceptionThrown() {
    Problem.Extension extensionWithNullValue = Problem.extension("key1", null);

    assertThrows(
        NullPointerException.class,
        () ->
            new Problem(
                URI.create("https://example.com/error"),
                "Test Error",
                400,
                "Test detail",
                URI.create("https://example.com/instance"),
                extensionWithNullValue));
  }

  @Test
  void givenMapWithNullValues_whenCreatingProblem_thenNullPointerExceptionThrown() {
    Map<String, Object> mapWithNullValue = new java.util.HashMap<>();
    mapWithNullValue.put("key1", "value1");
    mapWithNullValue.put("key2", null);

    assertThrows(
        NullPointerException.class,
        () ->
            new Problem(
                URI.create("https://example.com/error"),
                "Test Error",
                400,
                "Test detail",
                URI.create("https://example.com/instance"),
                mapWithNullValue));
  }
}
