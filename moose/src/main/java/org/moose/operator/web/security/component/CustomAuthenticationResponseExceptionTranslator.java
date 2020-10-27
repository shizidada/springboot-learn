package org.moose.operator.web.security.component;

import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.exception.BusinessAuthenticationException;
import org.moose.operator.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-07-29 23:07:23:07
 * @see org.moose.operator.web.security.component
 */
@Slf4j
@Component
public class CustomAuthenticationResponseExceptionTranslator
    implements WebResponseExceptionTranslator<BusinessAuthenticationException> {

  @Override
  public ResponseEntity<BusinessAuthenticationException> translate(Exception e) throws Exception {
    log.error("CustomAuthenticationResponseExceptionTranslator :: ", e);

    if (e instanceof InvalidGrantException) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(
              new BusinessAuthenticationException(e.getMessage(), ResultCode.NOT_LOGIN.getCode()));
    }

    if (e instanceof InvalidTokenException) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(new BusinessAuthenticationException(e.getMessage(), ResultCode.TOKEN_INVALID.getCode()));
    }

    if (e instanceof InternalAuthenticationServiceException ||
        e instanceof InvalidRequestException ||
        e instanceof HttpRequestMethodNotSupportedException) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(new BusinessAuthenticationException(e.getMessage(), ResultCode.LOGIN_SERVER_ERROR.getCode()));
    }

    if (e instanceof BusinessException) {
      BusinessException ex = (BusinessException) e;
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(new BusinessAuthenticationException(ex.getMessage(), ex.getCode()));
    }

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new BusinessAuthenticationException(e.getMessage(),
            ResultCode.UN_KNOWN_ERROR.getCode()));
  }
}
