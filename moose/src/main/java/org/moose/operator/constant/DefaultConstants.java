package org.moose.operator.constant;

/**
 * @author taohua
 */
public interface DefaultConstants {

  /**
   * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
   */
  String DEFAULT_PARAMETER_NAME_PHONE = "phone";

  /**
   * 账号
   */
  String DEFAULT_LOGIN_USERNAME_PARAMETER = "accountName";

  /**
   * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
   */
  String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

  /**
   * 密码
   */
  String DEFAULT_LOGIN_PASSWORD_PARAMETER = "password";
}
