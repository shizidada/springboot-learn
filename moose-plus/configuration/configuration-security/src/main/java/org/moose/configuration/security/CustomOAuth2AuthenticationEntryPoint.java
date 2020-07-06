package org.moose.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-25 14:02:14:02
 * @see org.moose.configuration
 */
@Slf4j
public class CustomOAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    log.info("AuthenticationEntryPoint 授权异常 [{}] [{}]", authException.getClass().getName(),
        authException.getMessage());

    Map<String, Object> map = new HashMap<String, Object>(16);
    Throwable cause = authException.getCause();
    if (cause instanceof InvalidTokenException) {
      map.put("code", ResultCode.OAUTH_INVALID_TOKEN.getCode());
      map.put("message", ResultCode.OAUTH_INVALID_TOKEN.getMessage());
    } else {
      map.put("code", ResultCode.OAUTH_ERROR.getCode());
      map.put("message", authException.getMessage());
    }
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(response.getOutputStream(), map);
    } catch (Exception e) {
      throw new ServletException();
    }
  }
}
