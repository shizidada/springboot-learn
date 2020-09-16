package org.moose.operator.model.emun;

/**
 * @author taohua
 */

public enum SmsCodeEnum {

  /**
   * 注册
   */
  REGISTER("register"),

  /**
   * sms login
   */
  SMS_LOGIN("sms_login"),

  /**
   * 重置密码
   */
  REST_PASSWORD("rest_password");

  private String value;

  SmsCodeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
