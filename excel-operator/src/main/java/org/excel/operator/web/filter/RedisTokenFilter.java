package org.excel.operator.web.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.web.security.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author taohua
 */
@Slf4j
public class RedisTokenFilter extends OncePerRequestFilter {

  private final CustomAuthenticationFailureHandler authenticationFailureHandler;

  //  private RedisTemplate redisTemplate;

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Value("${system.security.anonymous-urls}")
  private String[] anonymousUrls;

  public RedisTokenFilter(CustomAuthenticationFailureHandler customAuthenticationFailureHandler
      /*RedisTemplate redisTemplate*/) {
    this.authenticationFailureHandler = customAuthenticationFailureHandler;
    //    this.redisTemplate = redisTemplate;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  )
      throws ServletException, IOException {
    if (match(request)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = request.getParameter("token");
    String uri = request.getRequestURI();
    log.info("RedisTokenFilter then token :: {} uri :: {}", token, uri);
    if (StringUtils.isAllBlank(token)) {
      authenticationFailureHandler.onAuthenticationFailure(
          request,
          response,
          new BusinessException(ResultCode.TOKEN_VALIDATE_EMPTY)
      );
      return;
    }
    // TODO:
    // Object redisToken = redisTemplate.opsForValue().get(token);
    filterChain.doFilter(request, response);
  }

  private boolean match(HttpServletRequest request) {
    boolean isMatch = false;
    for (String url : anonymousUrls) {
      if (pathMatcher.match(url, request.getRequestURI())) {
        isMatch = true;
      }
    }
    return isMatch;
  }
}
