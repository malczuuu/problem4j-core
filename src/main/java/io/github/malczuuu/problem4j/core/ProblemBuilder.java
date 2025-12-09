package io.github.malczuuu.problem4j.core;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

/**
 * Builder interface for constructing {@link Problem} instances.
 *
 * <p>Provides a fluent API to set standard fields and custom extensions before creating an
 * immutable {@link Problem}.
 */
public interface ProblemBuilder {

  /**
   * Sets the problem type URI.
   *
   * @param type the URI identifying the problem type
   * @return this builder instance for chaining
   */
  ProblemBuilder type(URI type);

  /**
   * Sets the problem type from a string representation of a URI.
   *
   * @param type string URI identifying the problem type
   * @return this builder instance for chaining
   * @throws IllegalArgumentException if the string is not a valid URI
   */
  ProblemBuilder type(String type);

  /**
   * Sets the short, human-readable title for the problem.
   *
   * @param title the problem title
   * @return this builder instance for chaining
   */
  ProblemBuilder title(String title);

  /**
   * Sets the HTTP status code for this problem.
   *
   * @param status HTTP status code
   * @return this builder instance for chaining
   */
  ProblemBuilder status(int status);

  /**
   * Sets the HTTP status code using a {@link ProblemStatus} enum.
   *
   * @param status the {@link ProblemStatus} representing the HTTP status
   * @return this builder instance for chaining
   */
  ProblemBuilder status(ProblemStatus status);

  /**
   * Sets a detailed, human-readable description of this problem instance.
   *
   * @param detail the detail message
   * @return this builder instance for chaining
   */
  ProblemBuilder detail(String detail);

  /**
   * Sets the URI identifying this specific occurrence of the problem.
   *
   * @param instance the instance URI
   * @return this builder instance for chaining
   */
  ProblemBuilder instance(URI instance);

  /**
   * Sets the instance URI from a string representation.
   *
   * @param instance string URI identifying the problem occurrence
   * @return this builder instance for chaining
   * @throws IllegalArgumentException if the string is not a valid URI
   */
  ProblemBuilder instance(String instance);

  /**
   * Adds a single custom extension.
   *
   * @param name the extension key
   * @param value the extension value
   * @return this builder instance for chaining
   */
  ProblemBuilder extension(String name, Object value);

  /**
   * Adds multiple custom extensions from a map.
   *
   * @param extensions map of extension keys and values
   * @return this builder instance for chaining
   */
  ProblemBuilder extension(Map<String, Object> extensions);

  /**
   * Adds multiple custom extensions from varargs of {@link Problem.Extension}.
   *
   * @param extensions array of extensions
   * @return this builder instance for chaining
   */
  ProblemBuilder extension(Problem.Extension... extensions);

  /**
   * Adds multiple custom extensions from a collection of {@link Problem.Extension}.
   *
   * @param extensions collection of extensions
   * @return this builder instance for chaining
   */
  ProblemBuilder extension(Collection<Problem.Extension> extensions);

  /**
   * Builds an immutable {@link Problem} instance with the configured properties and extensions.
   *
   * <p>Default value evaluation (all defaults are applied at build time):
   *
   * <ul>
   *   <li>If no type was provided, the resulting {@code Problem} will use {@code
   *       Problem#BLANK_TYPE}.
   *   <li>If no title was provided, but the numeric status corresponds to a known {@code
   *       ProblemStatus}, the builder will use the matching {@code ProblemStatus#getTitle()} as the
   *       problem title.
   *   <li>The numeric status defaults to <code>0</code> when not set; a title will not be derived
   *       from status when it is <code>0</code> or when it does not map to any known {@code
   *       ProblemStatus}.
   *   <li>Any extensions configured on the builder will be present on the created {@code Problem}.
   * </ul>
   *
   * @return a new {@link Problem} instance
   */
  Problem build();

  /**
   * Alias for {@link #type(URI)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withType(URI type) {
    return type(type);
  }

  /**
   * Alias for {@link #type(String)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withType(String type) {
    return type(type);
  }

  /**
   * Alias for {@link #title(String)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withTitle(String title) {
    return title(title);
  }

  /**
   * Alias for {@link #status(int)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withStatus(int status) {
    return status(status);
  }

  /**
   * Alias for {@link #status(ProblemStatus)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withStatus(ProblemStatus status) {
    return status(status);
  }

  /**
   * Alias for {@link #detail(String)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withDetail(String detail) {
    return detail(detail);
  }

  /**
   * Alias for {@link #instance(URI)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withInstance(URI instance) {
    return instance(instance);
  }

  /**
   * Alias for {@link #instance(String)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withInstance(String instance) {
    return instance(instance);
  }

  /**
   * Alias for {@link #extension(String, Object)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withExtension(String name, Object value) {
    return extension(name, value);
  }

  /**
   * Alias for {@link #extension(Map)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withExtension(Map<String, Object> extensions) {
    return extension(extensions);
  }

  /**
   * Alias for {@link #extension(Problem.Extension...)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withExtension(Problem.Extension... extensions) {
    return extension(extensions);
  }

  /**
   * Alias for {@link #extension(Collection)}.
   *
   * <p>Convenience alias for the corresponding method, supporting both fluent ({@code x(...)}) and
   * explicit builder ({@code withX(...)}) naming styles.
   */
  default ProblemBuilder withExtension(Collection<Problem.Extension> extensions) {
    return extension(extensions);
  }
}
