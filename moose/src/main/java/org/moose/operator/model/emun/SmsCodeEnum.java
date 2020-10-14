package org.moose.operator.model.emun;

import org.apache.commons.lang3.StringUtils;
import org.moose.operator.constant.SmsTypeConstants;

/**
 * @author taohua
 */

public enum SmsCodeEnum {

  /**
   * 注册
   */
  REGISTER(SmsTypeConstants.REGISTER),

  /**
   * sms login
   */
  SMS_LOGIN(SmsTypeConstants.SMS_LOGIN),

  /**
   * reset phone number
   */
  RESET_PHONE(SmsTypeConstants.RESET_PHONE),

  /**
   * 重置密码
   */
  RESET_PASSWORD(SmsTypeConstants.RESET_PASSWORD);

  private String value;

  SmsCodeEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static boolean isExist(String value) {
    if (StringUtils.isEmpty(value)) {
      return Boolean.FALSE;
    }
    for (SmsCodeEnum smsCodeEnum : SmsCodeEnum.values()) {
      if (StringUtils.equals(smsCodeEnum.value, value)) {
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }
}
