package org.excel.operator.web.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
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
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Resource private ObjectMapper objectMapper;

  @Override public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    ServletOutputStream writer = response.getOutputStream();
    // TODO: save to redis ???
    log.info("CustomAuthenticationSuccessHandler 用户[{}]登录成功。", principal.getUsername());
    objectMapper.writeValue(writer, new ResponseResult<>(true, "登录成功"));
  }
}
