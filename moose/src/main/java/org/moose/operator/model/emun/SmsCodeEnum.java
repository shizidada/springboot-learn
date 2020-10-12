package org.moose.operator.model.emun;

import org.moose.operator.constant.SmsTypes;

/**
 * @author taohua
 */

public enum SmsCodeEnum {

  /**
   * 注册
   */
  REGISTER(SmsTypes.REGISTER),

  /**
   * sms login
   */
  SMS_LOGIN(SmsTypes.SMS_LOGIN),

  /**
   * reset phone number
   */
  RESET_PHONE(SmsTypes.RESET_PHONE),

  /**
   * 重置密码
   */
  RESET_PASSWORD(SmsTypes.RESET_PASSWORD);

  private String value;

  SmsCodeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
