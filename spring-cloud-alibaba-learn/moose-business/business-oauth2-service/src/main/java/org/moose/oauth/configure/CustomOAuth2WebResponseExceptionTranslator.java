package org.moose.oauth.configure;

import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 自定义响应异常转换器，转换 OAuth2Exception
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/14 23:40
 * @see org.moose.oauth.configure
 */
@Component
@Slf4j
public class CustomOAuth2WebResponseExceptionTranslator
    implements WebResponseExceptionTranslator {

  //("customOAuth2WebResponseExceptionTranslator")

  @Override
  public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
    log.info("WebResponseExceptionTranslator 【{}】", e.getMessage());
    if (e instanceof InvalidGrantException) {
      OAuth2Exception exception = (OAuth2Exception) e;
      return ResponseEntity
          .status(exception.getHttpErrorCode())
          .body(new CustomOAuth2Exception(exception.getMessage()));
    }
    return ResponseEntity
        .status(ResultCode.UNKNOWN.getCode())
        .body(new CustomOAuth2Exception(e.getMessage()));
  }
}
