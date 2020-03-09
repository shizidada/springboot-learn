package org.excel.operator.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/19 18:47
 * @see org.excel.operator.exception
 */
public class BusinessException extends AuthenticationException {

  private String message;

  private Integer code;

  public BusinessException(String message, Integer code) {
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
