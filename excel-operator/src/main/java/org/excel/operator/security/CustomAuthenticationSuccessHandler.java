package org.excel.operator.security;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.excel.operator.common.api.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 21:54
 * @see org.excel.operator.component
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

  @Override public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    logger.info(" >>>> CustomAuthenticationSuccessHandler >>>> 用户登录成功。");
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    String json =
        JSON.toJSONString(ResponseResult.success(authentication.getDetails()));
    response.getWriter().write(json);
  }
}
