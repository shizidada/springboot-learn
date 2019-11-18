package org.excel.operator.common.api;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/4 20:10
 * @see org.excel.operator.common
 */
public enum ResponseCode {
  /**
   * 成功
   */
  SUCCESS(200, "请求成功"),

  /**
   * 失败
   */
  FAIL(400, "请求失败");

  private Integer code;

  private String message;

  ResponseCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}