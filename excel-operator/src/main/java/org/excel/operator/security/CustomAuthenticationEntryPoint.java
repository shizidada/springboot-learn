package org.excel.operator.security;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import org.excel.operator.common.api.ResponseResult;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

  @Override public void commence(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException e)
      throws IOException, ServletException {
    logger.info("用户未登录");
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    ServletOutputStream writer = response.getOutputStream();
    writer.write(JSON.toJSONString(ResponseResult.fail(HttpServletResponse.SC_FORBIDDEN, e.getMessage())).getBytes());
    writer.close();
  }
}
