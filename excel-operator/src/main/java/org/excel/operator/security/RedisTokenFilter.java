package org.excel.operator.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.exception.RedisTokenException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author taohua
 */
@Slf4j
public class RedisTokenFilter extends OncePerRequestFilter {

  private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  private RedisTemplate redisTemplate;

  public RedisTokenFilter(CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
      RedisTemplate redisTemplate) {
    this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    this.redisTemplate = redisTemplate;
  }

  @Override protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    log.info("---------------------------- RedisTokenFilter ---------------------------");
    String token = httpServletRequest.getParameter("token");
    Object redisToken = redisTemplate.opsForValue().get(token);
    if (redisToken == null) {
      customAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest,
          httpServletResponse,
          new BusinessException(ResultCode.TOKEN_VALIDATE_FAIL.getMessage(),
              ResultCode.TOKEN_VALIDATE_FAIL.getCode()));
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
