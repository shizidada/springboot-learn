package org.moose.commons.base.code;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/5 19:55
 * @see org.moose.commons.base.dto
 */
public enum SmsCode {
  // 600001

  /**
   * 短信验证码不存在
   */
  SMS_CODE_NOT_FOUNT(600001, "短信验证码不存在"),

  /**
   * 短信验证码不能为空
   */
  SMS_CODE_MUST_NOT_BE_NULL(600002, "短信验证码不能为空"),

  /**
   * 短信验证码错误
   */
  SMS_CODE_ERROR(600003, "短信验证码错误"),

  /**
   * 短信体不能为空
   */
  SMS_CODE_BODY_MUST_NOT_BE_NULL(600004, "解析短信体为空"),

  /**
   * 短信验证解析错误
   */
  SMS_CODE_BODY_PARSE_ERROR(600005, "短信验证解析错误"),

  /**
   * 短信发送失败
   */
  SMS_CODE_SEND_FAIL(600006, "短信发送失败"),

  /**
   * 短信校验码已过期
   */
  SMS_CODE_EXPIRED(600007, "短信校验码已过期");

  private final Integer code;

  private final String message;

  SmsCode(Integer code, String message) {
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
