package org.learn.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.ResultCode;
import org.learn.exception.ExceptionModel;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;

@Slf4j
public class AuthExceptionUtil {

    private AuthExceptionUtil() {
    }

    public static ExceptionModel processException(Exception ex) {
        log.info("AuthExceptionUtil :: {} {}", ex, ex.getMessage());
        ExceptionModel exceptionModel = new ExceptionModel();
        if (ex instanceof SignatureException) {
            exceptionModel.setMessage(ResultCode.JWT_SIGNATURE.getMessage());
            exceptionModel.setCode(ResultCode.JWT_SIGNATURE.getCode());
        } else if (ex instanceof ExpiredJwtException) {
            exceptionModel.setMessage(ResultCode.JWT_EXPIRED.getMessage());
            exceptionModel.setCode(ResultCode.JWT_EXPIRED.getCode());
        } else if (ex instanceof DisabledException) {
            exceptionModel.setMessage(ResultCode.MEMBER_DISABLED.getMessage());
            exceptionModel.setCode(ResultCode.MEMBER_DISABLED.getCode());
        } else if (ex instanceof BadCredentialsException) {
            exceptionModel.setMessage(ResultCode.MEMBER_PASSWORD_NOT_EXIST.getMessage());
            exceptionModel.setCode(ResultCode.MEMBER_PASSWORD_NOT_EXIST.getCode());
        } else if (ex instanceof InsufficientAuthenticationException
                || ex instanceof IllegalArgumentException
        ) {
            exceptionModel.setMessage(ResultCode.UNAUTHORIZED.getMessage());
            exceptionModel.setCode(ResultCode.UNAUTHORIZED.getCode());
        } else if (ex instanceof AuthenticationServiceException) {
            exceptionModel.setCode(ResultCode.METHOD_NOT_ALLOWED.getCode());
            exceptionModel.setMessage(ex.getMessage());
        }
        return exceptionModel;
    }
}
