package org.moose.operator.model.domain;

/**
 * @author taohua
 */
public class SmsCodeDO extends BaseDO {

  private String phone;

  private String type;

  private String code;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override public String toString() {
    return "SmsCodeDO{" +
        "phone='" + phone + '\'' +
        ", type='" + type + '\'' +
        ", code='" + code + '\'' +
        '}';
  }
}
