package org.moose.operator.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.DefaultConstants;
import org.moose.operator.constant.HttpMethod;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.exception.BusinessException;
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
 * @see org.moose.operator.web.filter
 */
@Slf4j
public class LoginLimitFilter extends OncePerRequestFilter {

  /**
   * 存放所有需要校验验证码的url
   */
  private final Map<String, String> urlMap = new HashMap<>();

  private RedisTemplate<String, Object> redisTemplate;

  private AuthenticationFailureHandler authenticationFailureHandler;

  public LoginLimitFilter(RedisTemplate<String, Object> redisTemplate,
      AuthenticationFailureHandler authenticationFailureHandler) {
    this.redisTemplate = redisTemplate;
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  /**
   * 初始化要拦截的url配置信息
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    addUrlToMap(SecurityConstants.LOGIN_IN_URL, DefaultConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
  }

  /**
   * 如果不是登录请求，直接调用后面的过滤器链
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (ObjectUtils.isNotEmpty(urlMap.get(request.getRequestURI())) &&
        StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.POST)) {
      try {
        validate(request);
      } catch (BusinessException e) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, e);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
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

  /**
   * TODO: 限制 - 手动输入验证码？ - 一段时间内不让登录
   *
   * @param request
   * @throws IOException
   */
  private void validate(HttpServletRequest request) throws IOException {

    StringBuilder sb = new StringBuilder(RedisKeyConstants.LOGIN_LIMIT_KEY);

    String accountName = request.getParameter(DefaultConstants.DEFAULT_LOGIN_USERNAME_PARAMETER);
    if (StringUtils.isNotEmpty(accountName)) {
      sb.append(accountName);
    }

    String phoneNumber = request.getParameter(DefaultConstants.DEFAULT_PARAMETER_NAME_PHONE);
    if (StringUtils.isNotEmpty(phoneNumber)) {
      sb.append(phoneNumber);
    }

    // 计算登录次数
    String loginCountKey = sb.toString();
    Integer loginCount = (Integer) redisTemplate.opsForValue().get(loginCountKey);
    if (ObjectUtils.isEmpty(loginCount)) {
      redisTemplate.opsForValue()
          .set(loginCountKey, 1, SecurityConstants.LOGIN_TIME_OF_SECONDS,
              TimeUnit.SECONDS);
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
  }
}
