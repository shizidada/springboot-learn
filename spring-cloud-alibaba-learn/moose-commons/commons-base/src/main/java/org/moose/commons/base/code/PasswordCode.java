package org.moose.commons.base.code;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 20:03
 * @see org.moose.commons.base.dto
 */
public enum PasswordCode {
  // 200001
  /**
   * 密码不能为空
   */
  PASSWORD_MUST_NOT_BE_NULL(200001, "密码不能为空"),

  /**
   * 两次密码不一致
   */
  PASSWORD_NOT_RIGHT(200002, "两次密码不一致");

  private final Integer code;

  private final String message;

  PasswordCode(Integer code, String message) {
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
