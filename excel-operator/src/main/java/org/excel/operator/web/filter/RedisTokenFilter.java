package org.excel.operator.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.web.security.CustomAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author taohua
 */
@Slf4j
public class RedisTokenFilter extends OncePerRequestFilter {

  private CustomAuthenticationFailureHandler authenticationFailureHandler;

  //  private RedisTemplate redisTemplate;

  public RedisTokenFilter(CustomAuthenticationFailureHandler customAuthenticationFailureHandler
      /*RedisTemplate redisTemplate*/) {
    this.authenticationFailureHandler = customAuthenticationFailureHandler;
    //    this.redisTemplate = redisTemplate;
  }

  @Override protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    String token = httpServletRequest.getParameter("token");
    log.info("RedisTokenFilter then token :: {}", token);
    if (StringUtils.isAllBlank(token)) {
      authenticationFailureHandler.onAuthenticationFailure(
          httpServletRequest,
          httpServletResponse,
          new BusinessException(ResultCode.TOKEN_VALIDATE_EMPTY)
      );
      return;
    }
    // TODO:
    // Object redisToken = redisTemplate.opsForValue().get(token);
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
