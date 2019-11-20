package org.excel.operator.security;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.excel.operator.common.api.ResponseCode;
import org.excel.operator.common.api.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 21:56
 * @see org.excel.operator.component
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
  private Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

  @Override public void onAuthenticationFailure(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException e)
      throws IOException, ServletException {
    logger.info(" >>>> CustomAuthenticationFailureHandler >>>> 用户登录失败。");
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    String json =
        JSON.toJSONString(ResponseResult.fail(ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getCode(),
            ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getMessage()));
    response.getWriter().write(json);
  }
}
