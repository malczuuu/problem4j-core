package io.github.malczuuu.problem4j.core;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

final class ProblemBuilderImpl implements ProblemBuilder {

  private URI type;
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
  public ProblemBuilder type(String type) {
    return type != null ? type(URI.create(type)) : type((URI) null);
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
  public ProblemBuilder status(ProblemStatus status) {
    if (status != null) {
      if (title == null) {
        title = status.getTitle();
      }
      this.status = status.getStatus();
    }
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
  public ProblemBuilder instance(String instance) {
    return instance != null ? instance(URI.create(instance)) : instance((URI) null);
  }

  @Override
  public ProblemBuilder extension(String name, Object value) {
    if (name != null) {
      extensions.put(name, value);
    }
    return this;
  }

  @Override
  public ProblemBuilder extension(Map<String, Object> extensions) {
    if (extensions != null && !extensions.isEmpty()) {
      extensions.forEach(
          (key, value) -> {
            if (key != null) {
              this.extensions.put(key, value);
            }
          });
    }
    return this;
  }

  @Override
  public ProblemBuilder extension(Problem.Extension... extensions) {
    if (extensions != null && extensions.length > 0) {
      Stream.of(extensions)
          .filter(Objects::nonNull)
          .forEach(e -> this.extensions.put(e.getKey(), e.getValue()));
    }
    return this;
  }

  @Override
  public ProblemBuilder extension(Collection<Problem.Extension> extensions) {
    if (extensions != null && !extensions.isEmpty()) {
      extensions.stream()
          .filter(Objects::nonNull)
          .forEach(e -> this.extensions.put(e.getKey(), e.getValue()));
    }
    return this;
  }

  @Override
  public Problem build() {
    URI type = this.type;
    if (type == null) {
      type = Problem.BLANK_TYPE;
    }
    String title = this.title;
    if (title == null) {
      Optional<ProblemStatus> status = ProblemStatus.findValue(this.status);
      if (status.isPresent()) {
        title = status.get().getTitle();
      }
    }
    return new ProblemImpl(type, title, status, detail, instance, extensions);
  }
}
