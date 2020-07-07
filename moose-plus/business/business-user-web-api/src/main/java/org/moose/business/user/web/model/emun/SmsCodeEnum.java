package org.moose.business.user.web.model.emun;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/4 15:14
 * @see org.moose.business.web.user.web.model.emun
 */
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
