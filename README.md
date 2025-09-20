# Problem4J Core

[![JitPack](https://jitpack.io/v/malczuuu/problem4j-core.svg)](https://jitpack.io/#malczuuu/problem4j-core)
[![Build Status](https://github.com/malczuuu/problem4j-core/actions/workflows/gradle.yml/badge.svg)](https://github.com/malczuuu/problem4j-core/actions/workflows/gradle.yml)
[![Weekly Build Status](https://github.com/malczuuu/problem4j-core/actions/workflows/gradle-weekly.yml/badge.svg)](https://github.com/malczuuu/problem4j-core/actions/workflows/gradle-weekly.yml)

> Part of [`problem4j`][problem4j] package of libraries.

This library provides a minimal, framework-agnostic Java model of the [RFC 7807][rfc7807] "Problem Details" object, with
an immutable `Problem` class and a fluent `ProblemBuilder` for convenient construction.

It is intended to be used as a **foundation** for other libraries or applications that add framework-specific behavior
(e.g. Jackson, Spring).

## Table of Contents

- [Features](#features)
- [Example](#example)
- [Usage](#usage)

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
import io.github.malczuuu.problem4j.Problem;
import io.github.malczuuu.problem4j.ProblemException;

public class Class {
    public void method() {
        Problem problem =
                Problem.builder()
                        .type("https://example.com/errors/invalid-request")
                        .title("Invalid Request")
                        .status(400)
                        .detail("not a valid json")
                        .instance("https://example.com/instances/1234")
                        .build();

        throw new ProblemException(problem);
    }
}
```

## Usage

This library is available through [Jitpack][jitpack] repository. Add it along with repository in your dependency
manager.

1. Maven:
   ```xml
   <repositories>
       <repository>
           <id>jitpack.io</id>
           <url>https://jitpack.io</url>
       </repository>
   </repositories>
   <dependencies>
       <dependency>
           <groupId>com.github.malczuuu</groupId>
           <artifactId>problem4j-core</artifactId>
           <version>${problem4j.version}</version>
       </dependency>
   </dependencies>
   ```
2. Gradle (Groovy or Kotlin DSL):
   ```groovy
   repositories {
       maven { url = uri("https://jitpack.io") }
   }
   
   dependencies {
       implementation("com.github.malczuuu:problem4j-core:${problem4j.version}")
   }
    ```

## Remaining Libraries

- [`problem4j-jackson`][problem4j-spring-web] - Jackson module for serializing and deserializing `Problem`.
  objects.
- [`problem4j-spring-web`][problem4j-spring-web] - Spring Web module extending `ResponseEntityExceptionHandler` for
  handling exceptions and returning `Problem` responses.

[jitpack]: https://jitpack.io/#malczuuu/problem4j-core

[problem4j]: https://github.com/malczuuu/problem4j

[problem4j-jackson]: https://github.com/malczuuu/problem4j-jackson

[problem4j-spring-web]: https://github.com/malczuuu/problem4j-spring-web

[rfc7807]: https://datatracker.ietf.org/doc/html/rfc7807
