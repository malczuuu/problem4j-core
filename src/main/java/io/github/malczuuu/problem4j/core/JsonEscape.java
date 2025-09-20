package io.github.malczuuu.problem4j.core;

import java.util.HashMap;
import java.util.Map;

final class JsonEscape {

  private static final Map<Character, String> REPLACEMENTS = new HashMap<>();

  static {
    REPLACEMENTS.put('"', "\\\"");
    REPLACEMENTS.put('\\', "\\\\");
    REPLACEMENTS.put('\b', "\\b");
    REPLACEMENTS.put('\f', "\\f");
    REPLACEMENTS.put('\n', "\\n");
    REPLACEMENTS.put('\r', "\\r");
    REPLACEMENTS.put('\t', "\\t");
    REPLACEMENTS.put('/', "\\/");
  }

  static String escape(String value) {
    StringBuilder result = new StringBuilder();

    for (char character : value.toCharArray()) {
      if (shouldBeReplaced(character)) {
        replace(result, character);
      } else if (shouldBeHexed(character)) {
        hex(result, character);
      } else {
        result.append(character);
      }
    }

    return result.toString();
  }

  private static boolean shouldBeReplaced(char character) {
    return REPLACEMENTS.containsKey(character);
  }

  private static void replace(StringBuilder result, char character) {
    result.append(REPLACEMENTS.get(character));
  }

  private static boolean shouldBeHexed(char character) {
    return character <= '\u001F'
        || character >= '\u007F' && character <= '\u009F'
        || character >= '\u2000' && character <= '\u20FF';
  }

  /**
   * Appends the Unicode hexadecimal escape sequence for the given character to the result. The
   * escape sequence is in the format {@code "\\uXXXX"}, where {@code "XXXX"} is the uppercase
   * hexadecimal representation of the character code, padded with leading zeros to four digits.
   *
   * @param result the StringBuilder to append the escape sequence to
   * @param character the character to be escaped
   */
  private static void hex(StringBuilder result, char character) {
    String hexedCharacter = Integer.toHexString(character);
    result.append("\\u");
    result.append("0".repeat(4 - hexedCharacter.length()));
    result.append(hexedCharacter.toUpperCase());
  }
}
