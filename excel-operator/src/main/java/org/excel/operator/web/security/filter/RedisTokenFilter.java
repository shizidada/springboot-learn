package org.excel.operator.web.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.web.security.CustomAuthenticationFailureHandler;
import org.excel.operator.web.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author taohua
 */
@Slf4j
public class RedisTokenFilter extends OncePerRequestFilter {

  private final CustomAuthenticationFailureHandler authenticationFailureHandler;

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  private final AccountService accountService;

  @Value("${system.security.anonymous-urls}")
  private String[] anonymousUrls;

  public RedisTokenFilter(
      AccountService accountService,
      CustomAuthenticationFailureHandler authenticationFailureHandler) {
    this.accountService = accountService;
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  )
      throws ServletException, IOException {

    // 可以匿名访问
    if (match(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    // 判断是否登陆
    boolean isLogin = accountService.isLogin();
    if (isLogin) {
      filterChain.doFilter(request, response);
      return;
    }

    //String token = request.getParameter("token");
    //String uri = request.getRequestURI();
    //log.info("RedisTokenFilter then token :: {} uri :: {}", token, uri);
    //if (StringUtils.isAllBlank(token)) {
    //  authenticationFailureHandler.onAuthenticationFailure(
    //      request,
    //      response,
    //      new BusinessException(ResultCode.TOKEN_VALIDATE_EMPTY)
    //  );
    //  return;
    //}
    // TODO:
    // Object redisToken = redisTemplate.opsForValue().get(token);
    filterChain.doFilter(request, response);
  }

  /**
   * 判断请求的 url 是否可以匿名访问
   *
   * @param request HttpServletRequest
   * @return 是否可以匿名访问
   */
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
