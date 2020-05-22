package org.excel.operator.security;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
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
 * @see org.excel.operator.security
 * <p>
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 * <p>
 * AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override public void commence(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException e)
      throws IOException, ServletException {
    log.info(" >>>> CustomAuthenticationEntryPoint >>>> 用户未登录。");
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    ServletOutputStream writer = response.getOutputStream();
    writer.write(
        JSON.toJSONString(ResponseResult.fail(HttpServletResponse.SC_FORBIDDEN, e.getMessage()))
            .getBytes());
    writer.close();
  }
}
