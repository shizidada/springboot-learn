package org.excel.operator.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.excel.operator.common.api.ResultCode;
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
  private final static ObjectMapper objectMapper = new ObjectMapper();

  private Integer code;

  private String message;

  public BusinessException(String message) {
    super(message);
    this.message = message;
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
    this.message = String.format("%s %s", message, cause.getMessage());
  }

  public BusinessException(ResultCode resultCode) {
    super(resultCode.getMessage());
    this.message = resultCode.getMessage();
    this.code = resultCode.getCode();
  }

  public BusinessException(ResultCode resultCode, Object... args) {
    super(resultCode.getMessage());
    String message = resultCode.getMessage();
    try {
      message =
          String.format("%s %s", resultCode.getMessage(), objectMapper.writeValueAsString(args));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    this.message = message;
    this.code = resultCode.getCode();
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  @Override public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
