package org.moose.commons.base.exception;

import org.moose.commons.base.dto.ResultCode;

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

  private Integer code;

  public BusinessException() {
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException(ResultCode resultCode, Object... args) {
    super(String.format(resultCode.getMessage(), args));
    this.code = resultCode.getCode();
  }

  //public BusinessException(Integer code, String message) {
  //  super(message);
  //  this.code = code;
  //}

  //public BusinessException(Integer code, String msgFormat, Object... args) {
  //  super(String.format(msgFormat, args));
  //  this.code = code;
  //}

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }
}
