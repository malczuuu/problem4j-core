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

/**
 * Represents a problem detail according to the <a href="https://tools.ietf.org/html/rfc7807">RFC
 * 7807</a> specification.
 *
 * <p>Instances of {@link Problem} are intended to be immutable. They provide standard fields such
 * as:
 *
 * <ul>
 *   <li>{@code type} – a URI identifying the type of the problem
 *   <li>{@code title} – a short, human-readable summary of the problem
 *   <li>{@code status} – the HTTP status code generated for this problem
 *   <li>{@code detail} – a human-readable explanation specific to this occurrence
 *   <li>{@code instance} – a URI identifying the specific occurrence of the problem
 * </ul>
 *
 * In addition, custom extensions can be added to provide extra context.
 *
 * @deprecated use {@link io.github.problem4j.core.Problem}
 */
@Deprecated
public interface Problem extends io.github.problem4j.core.Problem {

  /**
   * Creates a new builder for constructing {@link Problem} instances.
   *
   * @return a new {@link ProblemBuilder}
   */
  static ProblemBuilder builder() {
    return new ProblemBuilderImpl();
  }

  /**
   * Creates a named extension for use in a {@link Problem}.
   *
   * @param key the extension key, must not be {@code null}
   * @param value the extension value
   * @return a new {@link Extension} instance
   * @throws IllegalArgumentException if the {@code key} is {@code null}
   */
  static Extension extension(String key, Object value) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
    return new ProblemImpl.ExtensionImpl(key, value);
  }

  /**
   * Converts this problem instance into a {@link Problem} builder, pre-populated with its values.
   * Useful for creating a modified copy.
   *
   * @return a builder with the current problem's values
   */
  ProblemBuilder toBuilder();

  /**
   * Represents a single key-value extension in a {@link Problem}.
   *
   * @deprecated use {@link io.github.problem4j.core.Problem.Extension}
   */
  @Deprecated
  interface Extension extends io.github.problem4j.core.Problem.Extension {}
}
