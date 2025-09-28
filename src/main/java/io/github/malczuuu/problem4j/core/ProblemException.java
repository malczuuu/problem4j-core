package io.github.malczuuu.problem4j.core;

/**
 * Runtime exception that wraps a {@link Problem} instance.
 *
 * <p>Provides a convenient way to throw exceptions associated with problem details according to RFC
 * 7807. The exception message is automatically generated from the problem's title, detail, and
 * status unless explicitly provided.
 */
public class ProblemException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /** The underlying {@link Problem} instance associated with this exception. */
  private final Problem problem;

  /**
   * Constructs a {@link ProblemException} with the given {@link Problem}.
   *
   * <p>The exception message is automatically generated from the problem's title, detail, and
   * status code.
   *
   * @param problem the problem instance to associate with this exception
   */
  public ProblemException(Problem problem) {
    super(produceExceptionMessage(problem));
    this.problem = problem;
  }

  /**
   * Constructs a {@link ProblemException} with a custom message and the given {@link Problem}.
   *
   * @param message custom exception message
   * @param problem the problem instance to associate with this exception
   */
  public ProblemException(String message, Problem problem) {
    super(message);
    this.problem = problem;
  }

  /**
   * Constructs a {@link ProblemException} with the given {@link Problem} and a cause.
   *
   * <p>The exception message is automatically generated from the problem's title, detail, and
   * status code.
   *
   * @param problem the problem instance to associate with this exception
   * @param cause the root cause of this exception
   */
  public ProblemException(Problem problem, Throwable cause) {
    super(produceExceptionMessage(problem), cause);
    this.problem = problem;
  }

  /**
   * Constructs a {@link ProblemException} with a custom message, the given {@link Problem}, and a
   * cause.
   *
   * @param message custom exception message
   * @param problem the problem instance to associate with this exception
   * @param cause the root cause of this exception
   */
  public ProblemException(String message, Problem problem, Throwable cause) {
    super(message, cause);
    this.problem = problem;
  }

  /**
   * Produces a string message for the exception based on the problem's title, detail, and status.
   *
   * <p>Format:
   *
   * <ul>
   *   <li>Title
   *   <li>Title: Detail
   *   <li>Title: Detail (code: STATUS)
   * </ul>
   *
   * If no information is available, returns {@code null}.
   *
   * @param problem the problem instance
   * @return a formatted exception message or {@code null} if empty
   */
  private static String produceExceptionMessage(Problem problem) {
    StringBuilder builder = new StringBuilder();

    if (problem.getTitle() != null) {
      builder.append(problem.getTitle());
    }

    if (problem.getDetail() != null) {
      if (builder.length() > 0) {
        builder.append(": ");
      }
      builder.append(problem.getDetail());
    }

    if (problem.getStatus() != 0) {
      if (builder.length() > 0) {
        builder.append(" ");
      }
      builder.append("(code: ").append(problem.getStatus()).append(")");
    }

    if (builder.length() == 0) {
      return null;
    }

    return builder.toString();
  }

  /**
   * Returns the underlying {@link Problem} associated with this exception.
   *
   * @return the {@link Problem} instance
   */
  public Problem getProblem() {
    return problem;
  }
}
