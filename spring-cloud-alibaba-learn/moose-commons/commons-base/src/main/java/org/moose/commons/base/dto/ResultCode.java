package org.moose.commons.base.dto;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/4 20:10
 * @see org.moose.commons.base.dto
 */
public enum ResultCode {
  /**
   * 成功
   */
  SUCCESS(200, "请求成功"),

  /**
   * 账号或密码错误
   */
  ACCOUNT_PASSWORD_ERROR(100001, "用户名或密码错误"),

  /**
   * OAuth2 授权异常
   */
  OAUTH_ERROR(200001, "授权异常"),

  /**
   * 参数校验
   */
  VALIDATE_FAIL(-10000, "参数校验失败"),

  /**
   * 网络错误
   */
  NET_WORK_UNKNOWN(460, "网络错误，请检查网络。"),

  /**
   * 未知错误
   */
  UNKNOWN(-99999, "未知错误");

  private final Integer code;

  private final String message;

  ResultCode(Integer code, String message) {
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
