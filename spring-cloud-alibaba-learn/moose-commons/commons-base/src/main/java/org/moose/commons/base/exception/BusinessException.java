package org.moose.commons.base.exception;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/14 22:11
 * @see org.moose.commons.base.exception
 */
public class BusinessException extends RuntimeException {

  private String message;

  private Integer code;

  public BusinessException(Integer code, String message) {
    super(message);
    this.message = message;
    this.code = code;
  }

  @Override public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }
}
