package org.learn.entity;

public class ImportExcelInfoDO {
  private Integer id;

  /**
   * iccid SIM卡卡号
   */
  private String iccid;

  /**
   * 运营商
   */
  private String operators;

  /**
   * 收货人
   */
  private String receiver;

  /**
   * 收货手机号
   */
  private String phone;

  /**
   * 收货地址
   */
  private String address;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getIccid() {
    return iccid;
  }

  public void setIccid(String iccid) {
    this.iccid = iccid;
  }

  public String getOperators() {
    return operators;
  }

  public void setOperators(String operators) {
    this.operators = operators;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
