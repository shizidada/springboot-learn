package org.excel.operator.model.emun;

public enum SmsCodeEnum {

  /**
   * 注册
   */
  REGISTER("register"),

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
