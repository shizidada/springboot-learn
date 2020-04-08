package org.moose.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.code.OAuth2Code;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 权限不足异常类
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-25 13:34:13:34
 * @see org.moose.configuration
 */
@Slf4j
public class CustomOAuth2AccessDeniedHandler implements AccessDeniedHandler {
  @Override public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    log.info("AccessDeniedHandler 权限被拒绝 [{}] [{}]", accessDeniedException.getClass().getName(),
        accessDeniedException.getMessage());

    response.setContentType("application/json;charset=UTF-8");
    Map<String, Object> map = new HashMap<String, Object>(16);
    map.put("code", OAuth2Code.OAUTH_UNAUTHORIZED.getCode());
    map.put("message", accessDeniedException.getMessage());
    ObjectMapper mapper = new ObjectMapper();
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(mapper.writeValueAsString(map));
  }
}
