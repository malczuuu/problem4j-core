package io.github.malczuuu.problem4j.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

final class ProblemImpl implements Problem {

  private static final long serialVersionUID = 1L;

  private final URI type;
  private final String title;
  private final int status;
  private final String detail;
  private final URI instance;
  private final Map<String, Object> extensions;

  ProblemImpl(
      URI type,
      String title,
      int status,
      String detail,
      URI instance,
      Map<String, Object> extensions) {
    this.type = type;
    this.title = title;
    this.status = status;
    this.detail = detail;
    this.instance = instance;
    this.extensions = new HashMap<>(extensions);
  }

  @Override
  public ProblemBuilder toBuilder() {
    ProblemBuilder builder =
        Problem.builder()
            .type(getType())
            .title(getTitle())
            .status(getStatus())
            .detail(getDetail())
            .instance(getInstance());

    for (String extension : getExtensions()) {
      builder = builder.extension(extension, getExtensionValue(extension));
    }

    return builder;
  }

  @Override
  public URI getType() {
    return this.type;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  @Override
  public int getStatus() {
    return this.status;
  }

  @Override
  public String getDetail() {
    return this.detail;
  }

  @Override
  public URI getInstance() {
    return this.instance;
  }

  @Override
  public Set<String> getExtensions() {
    return Collections.unmodifiableSet(extensions.keySet());
  }

  @Override
  public Object getExtensionValue(String name) {
    return extensions.get(name);
  }

  @Override
  public boolean hasExtension(String extension) {
    return extensions.containsKey(extension);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ProblemImpl problem = (ProblemImpl) obj;
    return Objects.equals(getType(), problem.getType())
        && Objects.equals(getTitle(), problem.getTitle())
        && getStatus() == problem.getStatus()
        && Objects.equals(getDetail(), problem.getDetail())
        && Objects.equals(getInstance(), problem.getInstance())
        && Objects.equals(extensions, problem.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getType(), getTitle(), getStatus(), getDetail(), getInstance(), extensions);
  }

  @Override
  public String toString() {
    List<String> lines = new ArrayList<>();
    if (getType() != null) {
      lines.add("\"type\" : \"" + quote(getType().toString()) + "\"");
    }
    if (getTitle() != null) {
      lines.add("\"title\" : \"" + quote(getTitle()) + "\"");
    }
    lines.add("\"status\" : " + getStatus());
    if (getDetail() != null) {
      lines.add("\"detail\" : \"" + quote(getDetail()) + "\"");
    }
    if (getInstance() != null) {
      lines.add("\"instance\" : \"" + quote(getInstance().toString()) + "\"");
    }

    getExtensions()
        .forEach(
            field -> {
              Object value = getExtensionValue(field);

              if (value == null) {
                return;
              }

              if (value instanceof String) {
                lines.add("\"" + field + "\" : \"" + quote((String) value) + "\"");
              } else if (value instanceof Number || value instanceof Boolean) {
                lines.add("\"" + field + "\" : " + value);
              } else {
                lines.add(getObjectLine(field, value));
              }
            });

    return lines.stream().collect(Collectors.joining(", ", "{ ", " }"));
  }

  private static String quote(String string) {
    return JsonEscape.escape(string);
  }

  private String getObjectLine(String field, Object value) {
    String className = value.getClass().getSimpleName();
    return "\"" + field + "\" : \"" + className + ":" + quote(value.toString()) + "\"";
  }

  static final class ExtensionImpl implements Problem.Extension {

    private final String key;
    private Object value;

    ExtensionImpl(String key, Object value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public String getKey() {
      return key;
    }

    @Override
    public Object getValue() {
      return value;
    }

    @Override
    public Object setValue(Object value) {
      this.value = value;
      return value;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      ExtensionImpl extension = (ExtensionImpl) obj;
      return Objects.equals(getKey(), extension.getKey())
          && Objects.equals(getValue(), extension.getValue());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getKey(), getValue());
    }

    @Override
    public String toString() {
      String valueLine;
      if (getValue() instanceof Number || getValue() instanceof Boolean) {
        valueLine = "\"value\": " + getValue();
      } else {
        valueLine = "\"value\": " + "\"" + quote(getValue().toString()) + "\"";
      }
      return "{ \"key\": \"" + quote(getKey()) + "\", " + valueLine + " }";
    }
  }
}
