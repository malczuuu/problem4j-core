package io.github.malczuuu.problem4j.core;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Problem implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  public static final URI BLANK_TYPE = URI.create("about:blank");
  public static final String CONTENT_TYPE = "application/problem+json";

  public static ProblemBuilder builder() {
    return new ProblemBuilderImpl();
  }

  public static ProblemBuilder builder(Problem problem) {
    ProblemBuilder builder =
        builder()
            .type(problem.getType())
            .title(problem.getTitle())
            .status(problem.getStatus())
            .detail(problem.getDetail())
            .instance(problem.getInstance());

    for (String extension : problem.getExtensions()) {
      builder = builder.extension(extension, problem.getExtensionValue(extension));
    }

    return builder;
  }

  public static Extension extension(String key, Object value) {
    return new Extension(key, value);
  }

  private final URI type;
  private final String title;
  private final int status;
  private final String detail;
  private final URI instance;
  private final Map<String, Object> extensions;

  public Problem(
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
    this.extensions = Map.copyOf(extensions);
  }

  public Problem(
      URI type, String title, int status, String detail, URI instance, Set<Extension> extensions) {
    this(type, title, status, detail, instance, buildMapFromExtensions(extensions));
  }

  public Problem(
      URI type, String title, int status, String detail, URI instance, Extension... extensions) {
    this(type, title, status, detail, instance, buildMapFromExtensions(extensions));
  }

  public Problem(
      URI type, String title, int status, String detail, URI instance, Object... extensions) {
    this(type, title, status, detail, instance, buildMapFromRawArgs(extensions));
  }

  private static Map<String, Object> buildMapFromExtensions(Set<Extension> extensions) {
    Map<String, Object> map = new HashMap<>(extensions.size());
    extensions.forEach(e -> map.put(e.getKey(), e.getValue()));
    return map;
  }

  private static Map<String, Object> buildMapFromExtensions(Extension[] extensions) {
    Map<String, Object> map = new HashMap<>(extensions.length);
    for (Problem.Extension e : extensions) {
      map.put(e.getKey(), e.getValue());
    }
    return map;
  }

  private static Map<String, Object> buildMapFromRawArgs(Object[] arguments) {
    Map<String, Object> map = new HashMap<>(arguments.length / 2);

    List<Object> valuesAsList = new ArrayList<>(Arrays.asList(arguments));
    if (valuesAsList.size() % 2 != 0) {
      valuesAsList.add(valuesAsList.size() - 1);
    }

    for (int i = 0; i < arguments.length; i += 2) {
      String key = arguments[i].toString();
      Object value = arguments[i + 1];
      map.put(key, value);
    }

    return map;
  }

  public URI getType() {
    return this.type;
  }

  public String getTitle() {
    return this.title;
  }

  public int getStatus() {
    return this.status;
  }

  public String getDetail() {
    return this.detail;
  }

  public URI getInstance() {
    return this.instance;
  }

  public Set<String> getExtensions() {
    return Collections.unmodifiableSet(extensions.keySet());
  }

  public Object getExtensionValue(String name) {
    return extensions.get(name);
  }

  public boolean hasExtension(String extension) {
    return extensions.containsKey(extension);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Problem problem = (Problem) o;
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
    List<String> lines = new ArrayList<>(4);
    if (getType() != null) {
      lines.add("\"type\": \"" + quote(getType().toString()) + "\"");
    }
    if (getTitle() != null) {
      lines.add("\"title\": \"" + quote(getTitle()) + "\"");
    }
    lines.add("\"status\": " + getStatus());
    if (getDetail() != null) {
      lines.add("\"detail\": \"" + quote(getDetail()) + "\"");
    }
    return lines.stream().collect(Collectors.joining(", ", "{ ", " }"));
  }

  public static String quote(String string) {
    return JsonEscape.escape(string);
  }

  public static final class Extension implements Map.Entry<String, Object> {

    private final String key;
    private Object value;

    private Extension(String key, Object value) {
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
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      Extension extension = (Extension) obj;
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
