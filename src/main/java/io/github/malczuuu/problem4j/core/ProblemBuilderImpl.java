package io.github.malczuuu.problem4j.core;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

final class ProblemBuilderImpl implements ProblemBuilder {

  private URI type = Problem.BLANK_TYPE;
  private String title;
  private int status = 0;
  private String detail;
  private URI instance;
  private final Map<String, Object> extensions = new LinkedHashMap<>();

  ProblemBuilderImpl() {}

  @Override
  public ProblemBuilder type(URI type) {
    this.type = type;
    return this;
  }

  @Override
  public ProblemBuilder title(String title) {
    this.title = title;
    return this;
  }

  @Override
  public ProblemBuilder status(int status) {
    this.status = status;
    return this;
  }

  @Override
  public ProblemBuilder detail(String detail) {
    this.detail = detail;
    return this;
  }

  @Override
  public ProblemBuilder instance(URI instance) {
    this.instance = instance;
    return this;
  }

  @Override
  public ProblemBuilder extension(String name, Object value) {
    extensions.put(name, value);
    return this;
  }

  public Problem build() {
    return new Problem(type, title, status, detail, instance, extensions);
  }
}
