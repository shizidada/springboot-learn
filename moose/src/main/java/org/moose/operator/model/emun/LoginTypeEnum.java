package org.moose.operator.model.emun;

/**
 * @author taohua
 */

public enum LoginTypeEnum {

  /**
   * 短信方式
   */
  SMS_CODE("sms_code"),

  /**
   * 密码方式
   */
  PASSWORD("password");

  private String value;

  LoginTypeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
