package org.moose.business.oauth.configure;

import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
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
 * @see org.moose.business.oauth.configure
 */
@Slf4j
@Component
public class CustomOAuth2ResponseExceptionTranslator
    implements WebResponseExceptionTranslator<CustomOAuth2Exception> {

  @Override
  public ResponseEntity<CustomOAuth2Exception> translate(Exception e) throws Exception {
    log.error("授权异常 ExceptionTranslator", e);

    if (e instanceof InternalAuthenticationServiceException ||
        e instanceof InvalidGrantException ||
        e instanceof InvalidRequestException) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new CustomOAuth2Exception(e.getMessage(), ResultCode.OAUTH_ERROR.getCode()));
    }

    if (e instanceof BusinessException) {
      BusinessException ex = (BusinessException) e;
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new CustomOAuth2Exception(ex.getMessage(), ex.getCode()));

    }

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new CustomOAuth2Exception(e.getMessage(), ResultCode.UNKNOWN.getCode()));
  }
}
