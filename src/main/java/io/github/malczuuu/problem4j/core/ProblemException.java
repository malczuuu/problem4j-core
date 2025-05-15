package io.github.malczuuu.problem4j.core;

import java.io.Serial;

public class ProblemException extends RuntimeException {

  @Serial private static final long serialVersionUID = 1L;

  private final Problem problem;

  public ProblemException(Problem problem) {
    super(produceExceptionMessage(problem));
    this.problem = problem;
  }

  public ProblemException(String message, Problem problem) {
    super(message);
    this.problem = problem;
  }

  public ProblemException(Problem problem, Throwable cause) {
    super(produceExceptionMessage(problem), cause);
    this.problem = problem;
  }

  public ProblemException(String message, Problem problem, Throwable cause) {
    super(message, cause);
    this.problem = problem;
  }

  private static String produceExceptionMessage(Problem problem) {
    StringBuilder builder = new StringBuilder();

    if (problem.getTitle() != null) {
      builder.append(problem.getTitle());
    }

    if (problem.getDetail() != null) {
      if (!builder.isEmpty()) {
        builder.append(": ");
      }
      builder.append(problem.getDetail());
    }

    if (problem.getStatus() != 0) {
      if (!builder.isEmpty()) {
        builder.append(" ");
      }
      builder.append("(code: ").append(problem.getStatus()).append(")");
    }

    if (builder.isEmpty()) {
      return null;
    }

    return builder.toString();
  }

  public Problem getProblem() {
    return problem;
  }
}
