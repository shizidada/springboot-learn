package org.moose.commons.base.code;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 19:54
 * @see org.moose.commons.base.dto
 */
public enum PhoneCode {
  // 500001

  /**
   * 手机号不存在
   */
  PHONE_NOT_FOUND(500001, "手机号不存在"),

  /**
   * 手机号不能为空
   */
  PHONE_MUST_NOT_BE_NULL(500002, "手机号不能为空"),

  /**
   * 手机号已存在
   */
  PHONE_IS_EXIST(200003, "手机号已存在");

  private final Integer code;

  private final String message;

  PhoneCode(Integer code, String message) {
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
