package org.moose.commons.base.code;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 19:58
 * @see org.moose.commons.base.dto
 */
public enum LoginCode {
  // 700001

  /**
   * 登录方式不能为空
   */
  LOGIN_TYPE_MUST_NOT_BE_NULL(700001, "登录方式不能为空"),

  /**
   * 登录方式不正确
   */
  LOGIN_TYPE_ERROR(700002, "登录方式不正确");

  private final Integer code;

  private final String message;

  LoginCode(Integer code, String message) {
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
