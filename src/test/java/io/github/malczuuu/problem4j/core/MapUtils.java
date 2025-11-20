package io.github.malczuuu.problem4j.core;

import java.util.HashMap;

final class MapUtils {

  /**
   * Creates a map from the given arguments in similar style to Java 9's Map.of().
   *
   * @param args an even number of arguments representing key-value pairs
   * @return a HashMap containing the provided key-value pairs
   */
  static HashMap<String, Object> mapOf(Object... args) {
    if (args.length % 2 != 0) {
      throw new IllegalArgumentException("args length must be even");
    }

    HashMap<String, Object> map = new HashMap<>();
    for (int i = 0; i < args.length; i += 2) {
      map.put((String) args[i], args[i + 1]);
    }
    return map;
  }

  private MapUtils() {}
}
