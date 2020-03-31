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
   * 账号不能为空
   */
  ACCOUNT_MUST_NOT_BE_NULL(100002, "账号不能为空"),

  /**
   * 手机号不存在
   */
  PHONE_NOT_FOUND(100021, "手机号不存在"),

  /**
   * 手机号不能为空
   */
  PHONE_MUST_NOT_BE_NULL(100022, "手机号不能为空"),

  /**
   * 密码不能为空
   */
  PASSWORD_MUST_NOT_BE_NULL(200001, "密码不能为空"),

  /**
   * 两次密码不一致
   */
  PASSWORD_NOT_RIGHT(200001, "两次密码不一致"),

  /**
   * 登录方式不能为空
   */
  LOGIN_TYPE_MUST_NOT_BE_NULL(300001, "登录方式不能为空"),

  /**
   * 登录方式不能为空
   */
  LOGIN_TYPE_ERROR(300002, "登录方式不正确"),

  /**
   * 短信验证码不存在
   */
  SMS_CODE_NOT_FOUNT(400001, "短信验证码不存在"),

  /**
   * 短信验证码不能为空
   */
  SMS_CODE_MUST_NOT_BE_NULL(400002, "短信验证码不能为空"),

  /**
   * 短信验证码错误
   */
  SMS_CODE_ERROR(400003, "短信验证码错误"),

  /**
   * OAuth2 授权异常
   */
  OAUTH_ERROR(500001, "授权异常"),

  /**
   * 参数校验
   */
  VALIDATE_FAIL(-10000, "参数校验失败"),

  /**
   * 网络错误
   */
  NET_WORK_UNKNOWN(460, "网络错误，请检查网络。"),

  /**
   * Invalid Token
   */
  INVALID_TOKEN(401, "Invalid Token"),

  /**
   * Unauthorized
   */
  UNAUTHORIZED(401, "Unauthorized"),

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
