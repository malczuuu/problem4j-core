package io.github.malczuuu.problem4j.core;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility enum for generic HTTP status codes, to be used with {@link ProblemBuilder} without having
 * to pass both {@link ProblemBuilder#title(String)} and {@link ProblemBuilder#status(int)}
 * separately, if creating {@link Problem} object representing standard HTTP error codes.
 *
 * <pre>{@code
 * // instead of calling two methods
 * Problem problem = Problem.builder()
 *     .title("Not Found")
 *     .status(404)
 *     .build();
 *
 * Problem problem2 = Problem.builder()
 *     .status(ProblemStatus.NOT_FOUND)
 *     .build();
 * }</pre>
 *
 * <p>This enum can be used to represent response status codes in a library-agnostic way, without
 * introducing dependencies on specific frameworks like Spring.
 *
 * <p><b>Referenced RFC sources (chronological)</b>:
 *
 * <ul>
 *   <li><b>1998-03</b> - RFC 2295: Transparent Content Negotiation in HTTP (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc2295">link</a>) - introduces 506 Variant
 *       Also Negotiates.
 *   <li><b>1998-04</b> - RFC 2324: Hyper Text Coffee Pot Control Protocol (HTCPCP/1.0) (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc2324">link</a>) - adds novelty code 418 I'm
 *       a teapot.
 *   <li><b>1999-02</b> - RFC 2518: HTTP Extensions for Distributed Authoring (WebDAV) (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc2518">link</a>) - defines 102 Processing,
 *       207 Multi-Status, 422 Unprocessable Entity, 423 Locked, 424 Failed Dependency.
 *   <li><b>1999-06</b> - RFC 2616: Hypertext Transfer Protocol -- HTTP/1.1 (Obsoleted) (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc2616">link</a>) - core set:
 *       100,101,200–206,300–305,400–417,500–505.
 *   <li><b>2000-02</b> - RFC 2774: An HTTP Extension Framework (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc2774">link</a>) - adds 510 Not Extended.
 *   <li><b>2002-01</b> - RFC 3229: Delta encoding in HTTP (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc3229">link</a>) - adds 226 IM Used.
 *   <li><b>2007-06</b> - RFC 4918: WebDAV (replacing RFC 2518) (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc4918">link</a>) - re-specifies
 *       102,207,422,423,424; adds 507 Insufficient Storage.
 *   <li><b>2010-05</b> - RFC 5842: Binding Extensions to WebDAV (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc5842">link</a>) - adds 208 Already Reported,
 *       508 Loop Detected.
 *   <li><b>2012-04</b> - RFC 6585: Additional HTTP Status Codes (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc6585">link</a>) - adds 428 Precondition
 *       Required, 429 Too Many Requests, 431 Request Header Fields Too Large, 511 Network
 *       Authentication Required.
 *   <li><b>2014-06</b> - RFC 7231: HTTP/1.1 Semantics and Content (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc7231">link</a>) - deprecates 305 Use Proxy;
 *       renames 413 Request Entity Too Large → Payload Too Large; 414 Request-URI Too Long → URI
 *       Too Long.
 *   <li><b>2015-04</b> - RFC 7538: Status Code 308 (Permanent Redirect) (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc7538">link</a>) - adds 308 Permanent
 *       Redirect.
 *   <li><b>2015-05</b> - RFC 7540: HTTP/2 (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc7540">link</a>) - adds 421 Misdirected
 *       Request.
 *   <li><b>2016-02</b> - RFC 7725: An HTTP Status Code to Report Legal Obstacles (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc7725">link</a>) - adds 451 Unavailable For
 *       Legal Reasons.
 *   <li><b>2018-01</b> - RFC 8297: Indicating Hints (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc8297">link</a>) - adds 103 Early Hints;
 *       obsoletes informal 103 Checkpoint name.
 *   <li><b>2018-09</b> - RFC 8470: Using Early Data in HTTP (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc8470">link</a>) - adds 425 Too Early.
 *   <li><b>2022-06</b> - RFC 9110: HTTP Semantics (<a
 *       href="https://datatracker.ietf.org/doc/html/rfc9110">link</a>) - renames 413 Payload Too
 *       Large → Content Too Large; 416 Requested Range Not Satisfiable → Range Not Satisfiable; 422
 *       Unprocessable Entity → Unprocessable Content; consolidates prior semantics.
 * </ul>
 *
 * @see <a href="https://http.cat/">HTTP Cats</a>
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status">HTTP response
 *     status codes - HTTP | MDN</a>
 */
public enum ProblemStatus {

  /**
   * 100 Continue.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.2.1">RFC 9110
   *     §15.2.1</a>
   */
  CONTINUE(100, "Continue"),

  /**
   * 101 Switching Protocols.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.2.2">RFC 9110
   *     §15.2.2</a>
   */
  SWITCHING_PROTOCOLS(101, "Switching Protocols"),

  /**
   * 102 Processing (WebDAV).
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc2518#section-10.1">RFC 2518 §10.1</a>
   */
  PROCESSING(102, "Processing"),

  /**
   * 103 Checkpoint.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc8297#section-2">RFC 8297 §2</a>
   * @deprecated Renamed to {@link #EARLY_HINTS} by RFC 8297.
   */
  @Deprecated
  CHECKPOINT(103, "Checkpoint"),

  /**
   * 103 Early Hints.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc8297#section-2">RFC 8297 §2</a>
   */
  EARLY_HINTS(103, "Early Hints"),

  /**
   * 200 OK.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.3.1">RFC 9110
   *     §15.3.1</a>
   */
  OK(200, "OK"),

  /**
   * 201 Created.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.3.2">RFC 9110
   *     §15.3.2</a>
   */
  CREATED(201, "Created"),

  /**
   * 202 Accepted.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.3.3">RFC 9110
   *     §15.3.3</a>
   */
  ACCEPTED(202, "Accepted"),

  /**
   * 203 Non-Authoritative Information.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.3.4">RFC 9110
   *     §15.3.4</a>
   */
  NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),

  /**
   * 204 No Content.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.3.5">RFC 9110
   *     §15.3.5</a>
   */
  NO_CONTENT(204, "No Content"),

  /**
   * 205 Reset Content.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.3.6">RFC 9110
   *     §15.3.6</a>
   */
  RESET_CONTENT(205, "Reset Content"),

  /**
   * 206 Partial Content.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.3.7">RFC 9110
   *     §15.3.7</a>
   */
  PARTIAL_CONTENT(206, "Partial Content"),

  /**
   * 207 Multi-Status (WebDAV).
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc4918#section-11.1">RFC 4918 §11.1</a>
   */
  MULTI_STATUS(207, "Multi-Status"),

  /**
   * 208 Already Reported (WebDAV).
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc5842#section-7.1">RFC 5842 §7.1</a>
   */
  ALREADY_REPORTED(208, "Already Reported"),

  /**
   * 226 IM Used.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc3229#section-10.4.1">RFC 3229
   *     §10.4.1</a>
   */
  IM_USED(226, "IM Used"),

  /**
   * 300 Multiple Choices.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.4.1">RFC 9110
   *     §15.4.1</a>
   */
  MULTIPLE_CHOICES(300, "Multiple Choices"),

  /**
   * 301 Moved Permanently.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.4.2">RFC 9110
   *     §15.4.2</a>
   */
  MOVED_PERMANENTLY(301, "Moved Permanently"),

  /**
   * 302 Found.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.4.3">RFC 9110
   *     §15.4.3</a>
   */
  FOUND(302, "Found"),

  /**
   * 303 See Other.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.4.4">RFC 9110
   *     §15.4.4</a>
   */
  SEE_OTHER(303, "See Other"),

  /**
   * 304 Not Modified.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.4.5">RFC 9110
   *     §15.4.5</a>
   */
  NOT_MODIFIED(304, "Not Modified"),

  /**
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc7231#section-6.4.5">RFC 7231 §6.4.5</a>
   * @deprecated Obsoleted by RFC 7231. "Use Proxy" is no longer recommended.
   */
  @Deprecated
  USE_PROXY(305, "Use Proxy"),

  /**
   * 307 Temporary Redirect.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.4.8">RFC 9110
   *     §15.4.8</a>
   */
  TEMPORARY_REDIRECT(307, "Temporary Redirect"),

  /**
   * 308 Permanent Redirect.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc7538#section-3">RFC 7538 §3</a>
   */
  PERMANENT_REDIRECT(308, "Permanent Redirect"),

  /**
   * 400 Bad Request.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.1">RFC 9110
   *     §15.5.1</a>
   */
  BAD_REQUEST(400, "Bad Request"),

  /**
   * 401 Unauthorized.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.2">RFC 9110
   *     §15.5.2</a>
   */
  UNAUTHORIZED(401, "Unauthorized"),

  /**
   * 402 Payment Required.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.3">RFC 9110
   *     §15.5.3</a>
   */
  PAYMENT_REQUIRED(402, "Payment Required"),

  /**
   * 403 Forbidden.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.4">RFC 9110
   *     §15.5.4</a>
   */
  FORBIDDEN(403, "Forbidden"),

  /**
   * 404 Not Found.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.5">RFC 9110
   *     §15.5.5</a>
   */
  NOT_FOUND(404, "Not Found"),

  /**
   * 405 Method Not Allowed.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.6">RFC 9110
   *     §15.5.6</a>
   */
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

  /**
   * 406 Not Acceptable.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.7">RFC 9110
   *     §15.5.7</a>
   */
  NOT_ACCEPTABLE(406, "Not Acceptable"),

  /**
   * 407 Proxy Authentication Required.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.8">RFC 9110
   *     §15.5.8</a>
   */
  PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),

  /**
   * 408 Request Timeout.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.9">RFC 9110
   *     §15.5.9</a>
   */
  REQUEST_TIMEOUT(408, "Request Timeout"),

  /**
   * 409 Conflict.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.10">RFC 9110
   *     §15.5.10</a>
   */
  CONFLICT(409, "Conflict"),

  /**
   * 410 Gone.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.11">RFC 9110
   *     §15.5.11</a>
   */
  GONE(410, "Gone"),

  /**
   * 411 Length Required.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.12">RFC 9110
   *     §15.5.12</a>
   */
  LENGTH_REQUIRED(411, "Length Required"),

  /**
   * 412 Precondition Failed.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.13">RFC 9110
   *     §15.5.13</a>
   */
  PRECONDITION_FAILED(412, "Precondition Failed"),

  /**
   * 413 Request Entity Too Large.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.4.14">RFC 2616
   *     §10.4.14</a>
   * @deprecated Renamed to {@link #PAYLOAD_TOO_LARGE} in RFC 7231, then to {@link
   *     #CONTENT_TOO_LARGE} in RFC 9110.
   */
  @Deprecated
  REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),

  /**
   * 413 Payload Too Large.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc7231#section-6.5.11">RFC 7231
   *     §6.5.11</a>
   * @deprecated Renamed to {@link #CONTENT_TOO_LARGE} in RFC 9110.
   */
  @Deprecated
  PAYLOAD_TOO_LARGE(413, "Payload Too Large"),

  /**
   * 413 Content Too Large.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.14">RFC 9110
   *     §15.5.14</a>
   */
  CONTENT_TOO_LARGE(413, "Content Too Large"),

  /**
   * 414 Request URI Too Long.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.4.15">RFC 2616
   *     §10.4.15</a>
   * @deprecated Renamed to {@link #URI_TOO_LONG} by RFC 8297.
   */
  @Deprecated
  REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),

  /**
   * 414 URI Too Long.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.15">RFC 9110
   *     §15.5.15</a>
   */
  URI_TOO_LONG(414, "URI Too Long"),

  /**
   * 415 Unsupported Media Type.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.16">RFC 9110
   *     §15.5.16</a>
   */
  UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

  /**
   * 416 Requested Range Not Satisfiable.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10.4.17">RFC 2616
   *     §10.5.17</a>
   * @deprecated renamed to {@link #RANGE_NOT_SATISFIABLE} by RFC 9110.
   */
  @Deprecated
  REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),

  /**
   * 416 Range Not Satisfiable.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.17">RFC 9110
   *     §15.5.17</a>
   */
  RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),

  /**
   * 417 Expectation Failed.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.18">RFC 9110
   *     §15.5.18</a>
   */
  EXPECTATION_FAILED(417, "Expectation Failed"),

  /**
   * 418 I'm a teapot.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc2324#section-2.3.2">RFC 2324 §2.3.2</a>
   */
  I_AM_A_TEAPOT(418, "I'm a teapot"),

  /**
   * 421 Misdirected Request.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc7540#section-9.1.2">RFC 7540 §9.1.2</a>
   */
  MISDIRECTED_REQUEST(421, "Misdirected Request"),

  /**
   * 422 Unprocessable Entity.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc4918#section-11.2">RFC 4918 §11.2</a>
   * @deprecated renamed to {@link #UNPROCESSABLE_CONTENT} by RFC 9110.
   */
  @Deprecated
  UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),

  /**
   * 422 Unprocessable Content.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.21">RFC 9110
   *     §15.5.21</a>
   */
  UNPROCESSABLE_CONTENT(422, "Unprocessable Content"),

  /**
   * 423 Locked.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc4918#section-11.3">RFC 4918 §11.3</a>
   */
  LOCKED(423, "Locked"),

  /**
   * 424 Failed Dependency.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc4918#section-11.4">RFC 4918 §11.4</a>
   */
  FAILED_DEPENDENCY(424, "Failed Dependency"),

  /**
   * 425 Too Early.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc8470#section-5.2">RFC 8470 §5.2</a>
   */
  TOO_EARLY(425, "Too Early"),

  /**
   * 426 Upgrade Required.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.5.19">RFC 9110
   *     §15.5.19</a>
   */
  UPGRADE_REQUIRED(426, "Upgrade Required"),

  /**
   * 428 Precondition Required.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc6585#section-3">RFC 6585 §3</a>
   */
  PRECONDITION_REQUIRED(428, "Precondition Required"),

  /**
   * 429 Too Many Requests.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc6585#section-4">RFC 6585 §4</a>
   */
  TOO_MANY_REQUESTS(429, "Too Many Requests"),

  /**
   * 431 Request Header Fields Too Large.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc6585#section-5">RFC 6585 §5</a>
   */
  REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),

  /**
   * 451 Unavailable For Legal Reasons.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc7725#section-3">RFC 7725 §3</a>
   */
  UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

  /**
   * 500 Internal Server Error.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.1">RFC 9110
   *     §15.6.1</a>
   */
  INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

  /**
   * 501 Not Implemented.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.2">RFC 9110
   *     §15.6.2</a>
   */
  NOT_IMPLEMENTED(501, "Not Implemented"),

  /**
   * 502 Bad Gateway.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.3">RFC 9110
   *     §15.6.3</a>
   */
  BAD_GATEWAY(502, "Bad Gateway"),

  /**
   * 503 Service Unavailable.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.4">RFC 9110
   *     §15.6.4</a>
   */
  SERVICE_UNAVAILABLE(503, "Service Unavailable"),

  /**
   * 504 Gateway Timeout.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.5">RFC 9110
   *     §15.6.5</a>
   */
  GATEWAY_TIMEOUT(504, "Gateway Timeout"),

  /**
   * 505 HTTP Version Not Supported.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc9110#section-15.6.6">RFC 9110
   *     §15.6.6</a>
   */
  HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),

  /**
   * 506 Variant Also Negotiates.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc2295#section-8.1">RFC 2295 §8.1</a>
   */
  VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),

  /**
   * 507 Insufficient Storage (WebDAV).
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc4918#section-11.5">RFC 4918 §11.5</a>
   */
  INSUFFICIENT_STORAGE(507, "Insufficient Storage"),

  /**
   * 508 Loop Detected (WebDAV).
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc5842#section-7.2">RFC 5842 §7.2</a>
   */
  LOOP_DETECTED(508, "Loop Detected"),

  /** 509 Bandwidth Limit Exceeded (unofficial). */
  BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),

  /**
   * 510 Not Extended.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc2774#section-7">RFC 2774 §7</a>
   */
  NOT_EXTENDED(510, "Not Extended"),

  /**
   * 511 Network Authentication Required.
   *
   * @link <a href="https://datatracker.ietf.org/doc/html/rfc6585#section-6">RFC 6585 §6</a>
   */
  NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");

  /**
   * Lookup map from integer HTTP status code to {@link ProblemStatus} enum constant.
   *
   * <p>This map is created once at class initialization using {@link Arrays#stream(Object[])} and
   * {@link Collectors#toMap}. The map is used by {@link #findValue(int)} to provide an efficient
   * code-to-enum lookup.
   */
  private static final Map<Integer, ProblemStatus> STATUSES_BY_CODE =
      Arrays.stream(values())
          .collect(
              Collectors.toMap(
                  ProblemStatus::getStatus,
                  Function.identity(),
                  ProblemStatus::resolveDeprecations));

  /**
   * Return the {@link ProblemStatus} matching the given integer HTTP status code.
   *
   * @param status the HTTP status code to look up (for example {@code 404})
   * @return an {@link Optional} containing the matching {@link ProblemStatus} if present, or {@link
   *     Optional#empty()} if there is no enum constant for the provided code
   */
  public static Optional<ProblemStatus> findValue(int status) {
    return Optional.ofNullable(STATUSES_BY_CODE.get(status));
  }

  /**
   * Human-readable title of the HTTP status as commonly used in responses (for example {@code "Not
   * Found"} for 404).
   */
  private final String title;

  /** Integer HTTP status code (for example {@code 404}). */
  private final int status;

  /**
   * Construct a {@code ProblemStatus} enum constant.
   *
   * @param status integer HTTP status code
   * @param title human-readable title commonly associated with the status code
   */
  ProblemStatus(int status, String title) {
    this.title = title;
    this.status = status;
  }

  /**
   * Get the human-readable title associated with this status code.
   *
   * @return the title string (never {@code null})
   */
  public String getTitle() {
    return title;
  }

  /**
   * Get the integer HTTP status code for this enum constant.
   *
   * @return the numeric status code (for example {@code 200}, {@code 404})
   */
  public int getStatus() {
    return status;
  }

  /**
   * Resolves conflicts between two {@link ProblemStatus} enum constants that share the same HTTP
   * status code, favoring the non-deprecated constant if one exists.
   *
   * <p>This method is used during the initialization of the lookup map from integer HTTP status
   * codes to {@link ProblemStatus} constants. If existing is annotated with {@link Deprecated} and
   * the replacement is not, the replacement is returned. Otherwise, the existing constant is kept.
   *
   * @param existing the current {@link ProblemStatus} already present in the map
   * @param replacement the new {@link ProblemStatus} being considered for the same HTTP status code
   * @return the {@link ProblemStatus} to use in the lookup map, preferring non-deprecated constants
   */
  private static ProblemStatus resolveDeprecations(
      ProblemStatus existing, ProblemStatus replacement) {
    try {
      boolean existingDeprecated =
          existing.getClass().getField(existing.name()).getAnnotation(Deprecated.class) != null;

      boolean replacementDeprecated =
          replacement.getClass().getField(replacement.name()).getAnnotation(Deprecated.class)
              != null;

      if (existingDeprecated && !replacementDeprecated) {
        return replacement;
      } else {
        return existing;
      }
    } catch (NoSuchFieldException e) {
      return existing;
    }
  }
}
