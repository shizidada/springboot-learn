package org.moose.commons.base.code;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 19:53
 * @see org.moose.commons.base.dto
 */
public enum AccountCode {
  // 100001
  /**
   * 账号或密码错误
   */
  ACCOUNT_PASSWORD_ERROR(100001, "用户名或密码错误"),

  /**
   * 账号不能为空
   */
  ACCOUNT_MUST_NOT_BE_NULL(100002, "账号不能为空"),

  /**
   * 账号 ID 为空
   */
  ACCOUNT_ID_MUST_NOT_BE_NULL(100003, "账号 ID 为空"),

  /**
   * 账号名称已存在
   */
  ACCOUNT_NAME_IS_EXIST(100004, "账号名称已存在");

  private final Integer code;

  private final String message;

  AccountCode(Integer code, String message) {
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
