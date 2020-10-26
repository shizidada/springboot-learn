package org.moose.operator.constant;

/**
 * @author taohua
 */
public interface SecurityConstants {

  Integer SMS_TIME_OF_DAY = 60;

  Integer SMS_TIME_OF_TIMEOUT = 10 * 60;

  /**
   * 短信一天限制发送次数
   */
  Integer MAX_COUNT_OF_DAY = 6;

  /**
   * 设置在一段时间范围类不能连续登录超过 6 次
   */
  Integer LOGIN_TIME_OF_SECONDS = 60;

  /**
   * access token 有效时间
   * 1 day
   */
  Integer ACCESS_TOKEN_VALIDITY = 60 * 60 * 24;

  /**
   * refresh token 有效时间
   * 15 day
   */
  Integer REFRESH_TOKEN_VALIDITY = 60 * 60 * 24 * 15;

  String OAUTH2_CLIENT = "client";

  String OAUTH2_SECRET = "secret";
}
