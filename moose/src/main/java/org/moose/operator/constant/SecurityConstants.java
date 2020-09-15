package org.moose.operator.constant;

/**
 * @author taohua
 */
public interface SecurityConstants {

  String LOGIN_IN_URL = "/api/v1/account/login";

  String LOGIN_OUT_URL = "/api/v1/account/logout";

  String REGISTER_URL = "/api/v1/account/register";

  String LOGIN_STATUS_URL = "/api/v1/login/status";

  String SEND_SMS_CODE_URL = "/api/v1/sms/send";

  String GET_REFRESH_TOKEN_URL = "/api/v1/account/getRefreshToken";

  String REFRESH_TOKEN_URL = "/api/v1/account/refreshToken";

  Integer SMS_TIME_OF_DAY = 60;

  Integer SMS_TIME_OF_TIMEOUT = 15 * 60;

  /**
   * 短信一天限制发送次数
   */
  Integer MAX_COUNT_OF_DAY = 6;

  /**
   * 设置在一段时间范围类不能连续登录超过 6 次
   */
  Integer LOGIN_TIME_OF_SECONDS = 60;

  String OAUTH2_CLIENT = "client";

  String OAUTH2_SECRET = "secret";
}
