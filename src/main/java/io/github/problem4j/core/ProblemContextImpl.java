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
package io.github.problem4j.core;

import java.util.HashMap;
import java.util.Map;

class ProblemContextImpl implements ProblemContext {

  private final Map<String, String> delegate = new HashMap<>();

  @Override
  public boolean containsKey(String key) {
    return delegate.containsKey(key);
  }

  @Override
  public String get(String key) {
    return delegate.get(key);
  }

  @Override
  public String put(String key, String value) {
    return value == null ? delegate.remove(key) : delegate.put(key, value);
  }

  @Override
  public ProblemContext with(String key, String value) {
    put(key, value);
    return this;
  }
}
