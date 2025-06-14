package io.github.malczuuu.problem4j.core;

import java.net.URI;

public interface ProblemBuilder {

  ProblemBuilder type(URI type);

  ProblemBuilder type(String type);

  ProblemBuilder title(String title);

  ProblemBuilder status(int status);

  ProblemBuilder status(ProblemStatus status);

  ProblemBuilder detail(String detail);

  ProblemBuilder instance(URI instance);

  ProblemBuilder instance(String instance);

  ProblemBuilder extension(String name, Object value);

  Problem build();
}
