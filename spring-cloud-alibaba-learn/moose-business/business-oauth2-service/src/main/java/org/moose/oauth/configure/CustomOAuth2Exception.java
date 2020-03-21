package org.moose.oauth.configure;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义 OAuth2 异常，返回自定一次异常信息
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/14 23:47
 * @see org.moose.oauth.configure
 */
@JsonSerialize(using = CustomOAuth2ExceptionSerializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {
  private String message;
  private Integer code;

  public CustomOAuth2Exception(String message, Integer code) {
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
