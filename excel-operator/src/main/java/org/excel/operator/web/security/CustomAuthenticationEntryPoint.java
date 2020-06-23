package org.excel.operator.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 22:54
 * @see org.excel.operator.web.security
 * <p>
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 * <p>
 * AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Resource private ObjectMapper objectMapper;

  @Override public void commence(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException e)
      throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    ServletOutputStream writer = response.getOutputStream();
    String message = e.getMessage();
    Integer code = HttpServletResponse.SC_UNAUTHORIZED;
    if (e instanceof BusinessException) {
      BusinessException be = (BusinessException) e;
      message = be.getMessage();
      code = be.getCode();
    }
    if (e instanceof InsufficientAuthenticationException) {
      message = ResultCode.NOT_LOGIN.getMessage();
    }
    log.info("CustomAuthenticationEntryPoint code : [{}] message: [{}] errorMessage: [{}]",
        code, message, e.getMessage());
    objectMapper.writeValue(writer, new ResponseResult<>(code, message));
  }
}
