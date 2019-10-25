package org.learn.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author shizi
 */
public class ExcelInfo {

  /**
   * iccid SIM卡卡号
   */
  @ExcelProperty(value = "iccid", index = 0)
  private String iccid;

  /**
   * 运营商
   */
  @ExcelProperty(value = "期待更换运营商", index = 1)
  private String operators;

  /**
   * 收货人
   */
  @ExcelProperty(value = "收货人", index = 2)
  private String receiver;

  /**
   * 收货手机号
   */
  @ExcelProperty(value = "收货手机号", index = 3)
  private String phone;

  /**
   * 收货地址
   */
  @ExcelProperty(value = "收货地址", index = 4)
  private String address;

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

  @Override public String toString() {
    return "ExcelInfo{" +
        "iccid=" + iccid +
        ", operators='" + operators + '\'' +
        ", receiver='" + receiver + '\'' +
        ", phone='" + phone + '\'' +
        ", address='" + address + '\'' +
        '}';
  }
}
