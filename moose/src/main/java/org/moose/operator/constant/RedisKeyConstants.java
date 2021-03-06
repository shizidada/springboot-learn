package org.moose.operator.constant;

/**
 * @author taohua
 */
public interface RedisKeyConstants {

  /**
   * 记录手机号在一段时间类不能连续登录 save redis
   */
  String LOGIN_LIMIT_KEY = "moose:login:limit:";

  /**
   * 短信验证 key
   */
  String SMS_CODE_KEY = "moose:sms:code:%s_%s";

  /**
   * 发送短信到手机号码
   */
  String SMS_PHONE_KEY = "moose:sms:phone:";

  /**
   * 保存刷新 token
   */
  String REFRESH_TOKEN_KEY = "moose:refresh:token:%s";

  /**
   * moose privacy user info key
   */
  String USER_INFO_KEY = "moose:user:privacy:info:%s";

  /**
   * moose base user info key
   */
  String USER_BASE_INFO_KEY = "moose:user:base:info:%s";

  String USER_LIKED_KEY = "moose:user:liked:%s";
}
