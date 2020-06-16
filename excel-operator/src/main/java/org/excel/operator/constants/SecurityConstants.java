package org.excel.operator.constants;

/**
 * @author taohua
 */
public interface SecurityConstants {

  String LOGIN_IN_URL = "/api/v1/account/login";

  String LOGIN_OUT_URL = "/api/v1/account/logout";

  String REGISTER_URL = "/api/v1/account/register";

  String LOGIN_STATUS_URL = "/api/v1/login/status";

  String SMS_LOGIN_URL = "/api/v1/login/mobile";

  String SEND_SMS_CODE_URL = "/api/v1/sms/send";

  /**
   * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
   */
  String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

  /**
   * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
   */
  String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

  String SMS_KEY = "moose:sms:";
}
