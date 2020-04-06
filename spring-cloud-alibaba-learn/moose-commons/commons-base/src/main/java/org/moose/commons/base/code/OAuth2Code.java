package org.moose.commons.base.code;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 19:56
 * @see org.moose.commons.base.dto
 */
public enum OAuth2Code {
  // 400001

  /**
   * OAuth2 授权异常
   */
  OAUTH_ERROR(400001, "授权异常"),

  /**
   * Unauthorized
   */
  OAUTH_UNAUTHORIZED(400002, "Unauthorized"),

  /**
   * Invalid Token
   */
  OAUTH_INVALID_TOKEN(400003, "Invalid Token");

  private final Integer code;

  private final String message;

  OAuth2Code(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
