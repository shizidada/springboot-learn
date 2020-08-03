package org.excel.operator.constants;

public interface RedisKeyConstants {

  /**
   * 记录手机号在一段时间类不能连续登录 save redis
   */
  String LOGIN_FAIL_COUNT_KEY = "moose:login:fail:count:";

  /**
   * 短信验证 key
   */
  String SMS_CODE_KEY = "moose:sms:code:";

  /**
   * 发送短信到手机号码
   */
  String SMS_MOBILE_KEY = "moose:sms:mobile:";
}
