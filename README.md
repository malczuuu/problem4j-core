# Problem4J Core

[![Build Status](https://github.com/malczuuu/problem4j-core/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/malczuuu/problem4j-core/actions/workflows/gradle-build.yml)
[![Sonatype](https://img.shields.io/maven-central/v/io.github.malczuuu.problem4j/problem4j-core)](https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-core)
[![License](https://img.shields.io/github/license/malczuuu/problem4j-core)](https://github.com/malczuuu/problem4j-core/blob/main/LICENSE)

This library provides a minimal, framework-agnostic Java model of the [RFC 7807][rfc7807] "Problem Details" object, with
an immutable `Problem` class and a fluent `ProblemBuilder` for convenient construction.

It is intended to be used as a **foundation** for other libraries or applications that add framework-specific behavior
(e.g. Jackson, Spring).

## Table of Contents

- [Features](#features)
- [Example](#example)
- [Usage](#usage)
- [Problem4J Links](#problem4j-links)

## Features

- ✅ Immutable `Problem` data model
- ✅ Dedicated unchecked `ProblemException` to be used in error handling
- ✅ Builder pattern for fluent construction
- ✅ Serializable and easy to log or format
- ✅ HTTP-agnostic (no external dependencies)
- ✅ Follows [RFC 7807][rfc7807] semantics:
    - `type` (URI)
    - `title` (short summary)
    - `status` (numeric code)
    - `detail` (detailed description)
    - `instance` (URI to the specific occurrence)
    - custom field extensions

## Example

```java
import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;

Problem problem =
    Problem.builder()
        .type("https://example.com/errors/invalid-request")
        .title("Invalid Request")
        .status(400)
        .detail("not a valid json")
        .instance("https://example.com/instances/1234")
        .build();
throw new ProblemException(problem);
```

## Usage

Add library as dependency to Maven or Gradle. See the actual versions on [Maven Central][maven-central]. **Java 8** or
higher is required to use this library.

1. Maven:
   ```xml
   <dependencies>
       <dependency>
           <groupId>io.github.malczuuu.problem4j</groupId>
           <artifactId>problem4j-core</artifactId>
           <version>1.1.1</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Groovy or Kotlin DSL):
   ```groovy
   dependencies {
       implementation("io.github.malczuuu.problem4j:problem4j-core:1.1.1")
   }
    ```

For using snapshot versions [**Snapshots** chapter of`PUBLISHING.md`](PUBLISHING.md#snapshots).

## Problem4J Links

- [`problem4j-core`][problem4j-core] - Core library defining `Problem` model and `ProblemException`.
- [`problem4j-jackson`][problem4j-jackson] - Jackson module for serializing and deserializing `Problem` objects.
- [`problem4j-spring`][problem4j-spring] - Spring modules extending `ResponseEntityExceptionHandler` for handling
  exceptions and returning `Problem` responses.

[maven-central]: https://central.sonatype.com/artifact/io.github.malczuuu.problem4j/problem4j-core

[problem4j-core]: https://github.com/malczuuu/problem4j-core

[problem4j-jackson]: https://github.com/malczuuu/problem4j-jackson

[problem4j-spring]: https://github.com/malczuuu/problem4j-spring

[rfc7807]: https://datatracker.ietf.org/doc/html/rfc7807
