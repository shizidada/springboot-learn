package org.excel.operator.web.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.constants.RedisKeyConstants;
import org.excel.operator.constants.SecurityConstants;
import org.excel.operator.exception.BusinessException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-30 22:59:22:59
 * @see org.excel.operator.web.security.filter
 */
@Slf4j
public class LoginFailCountFilter extends OncePerRequestFilter implements InitializingBean {

  public static final String LOGIN_IN_URL = SecurityConstants.LOGIN_IN_URL;

  public static final String LOGIN_IN_POST_METHOD = "POST";

  /**
   * 存放所有需要校验验证码的url
   */
  private final Map<String, String> urlMap = new HashMap<>();

  private AuthenticationFailureHandler authenticationFailureHandler;

  private RedisTemplate<String, Object> redisTemplate;

  public LoginFailCountFilter() {
  }

  public LoginFailCountFilter(
      AuthenticationFailureHandler authenticationFailureHandler,
      RedisTemplate<String, Object> redisTemplate) {
    this.authenticationFailureHandler = authenticationFailureHandler;
    this.redisTemplate = redisTemplate;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (StringUtils.equals(request.getRequestURI(), LOGIN_IN_URL)
        && StringUtils.equalsIgnoreCase(
        request.getMethod(), LOGIN_IN_POST_METHOD)) {
      try {
        validate(request);
      } catch (BusinessException e) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        return;
      }
    }

    /**
     * 如果不是登录请求，直接调用后面的过滤器链
     */
    filterChain.doFilter(request, response);
  }

  /**
   * 初始化要拦截的url配置信息
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    addUrlToMap(LOGIN_IN_URL, SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
  }

  /**
   * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
   *
   * @param urlString
   * @param smsParam
   */
  protected void addUrlToMap(String urlString, String smsParam) {
    if (StringUtils.isNotBlank(urlString)) {
      String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
      for (String url : urls) {
        urlMap.put(url, smsParam);
      }
    }
  }

  private void validate(HttpServletRequest request) {
    String mobilePhone = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
    String accountName = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
    String loginCountKey = RedisKeyConstants.LOGIN_COUNT_MOBILE;
    if (mobilePhone != null) {
      loginCountKey += mobilePhone;
    }
    if (accountName != null) {
      loginCountKey += accountName;
    }
    // 计算登录次数
    Integer loginCount = (Integer) redisTemplate.opsForValue().get(loginCountKey);
    if (loginCount == null) {
      redisTemplate.opsForValue()
          .set(loginCountKey, 1, SecurityConstants.LOGIN_TIME_OF_SECONDS, TimeUnit.SECONDS);
    } else {
      // 判断手机号时间范围内，累计发送次数
      if (loginCount >= SecurityConstants.MAX_COUNT_OF_DAY) {
        throw new BusinessException(ResultCode.LOGIN_MAX_COUNT_FAIL);
      }
      redisTemplate.opsForValue().increment(loginCountKey);
      // TODO：重新设置过期时间
      //redisTemplate.expire(loginCountKey, SecurityConstants.LOGIN_TIME_OF_SECONDS,
      //    TimeUnit.SECONDS);
    }
    log.info("login key [{}] ", loginCountKey);
  }
}
