package org.learn.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.ResultCode;
import org.learn.exception.BusinessException;
import org.learn.exception.ExceptionModel;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Slf4j
public class AuthExceptionUtil {

    private AuthExceptionUtil() {
    }

    public static ExceptionModel processException(Exception ex) {
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
        } else if (ex instanceof BadCredentialsException
                || ex instanceof UsernameNotFoundException
        ) {
            exceptionModel.setMessage(ex.getMessage());
            exceptionModel.setCode(ResultCode.MEMBER_PASSWORD_NOT_EXIST.getCode());
        } else if (ex instanceof InsufficientAuthenticationException
                || ex instanceof IllegalArgumentException
        ) {
            exceptionModel.setMessage(ResultCode.UNAUTHORIZED.getMessage());
            exceptionModel.setCode(ResultCode.UNAUTHORIZED.getCode());
        } else if (ex instanceof AuthenticationServiceException
                || ex instanceof HttpRequestMethodNotSupportedException
        ) {
            exceptionModel.setCode(ResultCode.METHOD_NOT_ALLOWED.getCode());
            exceptionModel.setMessage(ex.getMessage());
        } else if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            exceptionModel.setMessage(businessException.getMessage());
            exceptionModel.setCode(businessException.getCode());
        } else {
            // 从枚举类中取出自定义的错误码和错误信息
            exceptionModel.setCode(ResultCode.UNKNOWN_ERROR.getCode());
            exceptionModel.setMessage(ex.getMessage());
        }
        log.info("AuthExceptionUtil :: message {} ex.toString :: {}", ex.getMessage(), ex.toString());
        return exceptionModel;
    }
}
