# Problem4J Core

[![JitPack](https://jitpack.io/v/malczuuu/problem4j-core.svg)](https://jitpack.io/#malczuuu/problem4j-core)
[![Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle.yml)
[![Weekly Build Status](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-weekly.yml/badge.svg)](https://github.com/malczuuu/problem4j-jackson/actions/workflows/gradle-weekly.yml)

> Part of [`problem4j`][problem4j] package of libraries.

This library provides a minimal, framework-agnostic Java model of the [RFC 7807][rfc7807] "Problem Details" object, with
an immutable `Problem` class and a fluent `ProblemBuilder` for convenient construction.

It is intended to be used as a **foundation** for other libraries or applications that add framework-specific behavior
(e.g. Jackson, Spring).

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

This library is available through [Jitpack][jitpack] repository. Add it along with repository in your dependency
manager.

```groovy
// build.gradle

repositories {
    // ...
    maven { url "https://jitpack.io/" }
}

dependencies {
    // ...
    implementation("com.github.malczuuu:problem4j-core:<version>")
}
```

[rfc7807]: https://datatracker.ietf.org/doc/html/rfc7807

[problem4j]: https://github.com/malczuuu/problem4j

[jitpack]: https://jitpack.io/#malczuuu/problem4j-core
