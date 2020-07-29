package org.excel.operator.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.excel.operator.web.security.component.CustomAuthenticationExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-07-29 23:08:23:08
 * @see org.excel.operator.web.security.component
 */
@JsonSerialize(using = CustomAuthenticationExceptionSerializer.class)
public class BusinessAuthenticationException extends OAuth2Exception {
  private String message;
  private Integer code;

  public BusinessAuthenticationException(String message, Integer code) {
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
