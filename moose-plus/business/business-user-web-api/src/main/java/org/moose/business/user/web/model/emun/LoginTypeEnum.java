package org.moose.business.user.web.model.emun;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/4 14:42
 * @see org.moose.business.web.user.web.model.emun
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
