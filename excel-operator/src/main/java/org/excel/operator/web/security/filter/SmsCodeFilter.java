package org.excel.operator.web.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.constants.CommonConstants;
import org.excel.operator.constants.RedisKeyConstants;
import org.excel.operator.constants.SecurityConstants;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.sender.model.ValidateCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-15 22:04:22:04
 * @see org.excel.operator.web.security.sms
 */
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

  private static final String SMS_CODE_FILTER_URL = SecurityConstants.SMS_LOGIN_URL;

  private static final String SMS_CODE_POST_METHOD = CommonConstants.METHOD_POST;

  private static final String SMS_CODE_GET_METHOD = CommonConstants.METHOD_GET;

  /**
   * 存放所有需要校验验证码的url
   */
  private final Map<String, String> urlMap = new HashMap<>();
  /**
   * 验证请求url与配置的url是否匹配的工具类
   */
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  private AuthenticationFailureHandler authenticationFailureHandler;

  private RedisTemplate<String, Object> redisTemplate;

  public SmsCodeFilter() {
  }

  public SmsCodeFilter(
      AuthenticationFailureHandler authenticationFailureHandler,
      RedisTemplate<String, Object> redisTemplate) {
    this.authenticationFailureHandler = authenticationFailureHandler;
    this.redisTemplate = redisTemplate;
  }

  /**
   * 初始化要拦截的url配置信息
   */
  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    addUrlToMap(SMS_CODE_FILTER_URL, SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
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

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (StringUtils.equals(request.getRequestURI(), SMS_CODE_FILTER_URL)
        && StringUtils.equalsIgnoreCase(
        request.getMethod(), SMS_CODE_POST_METHOD)) {
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
   * 获取校验码
   *
   * @param request
   * @return
   */
  private String getValidateCode(HttpServletRequest request) {
    String result = null;
    if (!StringUtils.equalsIgnoreCase(request.getMethod(), SMS_CODE_GET_METHOD)) {
      Set<String> urls = urlMap.keySet();
      for (String url : urls) {
        if (pathMatcher.match(url, request.getRequestURI())) {
          result = urlMap.get(url);
        }
      }
    }
    return result;
  }

  private void validate(HttpServletRequest request) {
    String smsCode = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
    if (StringUtils.isBlank(smsCode)) {
      throw new BusinessException(ResultCode.SMS_CODE_IS_EMPTY);
    }
    String mobile = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
    if (StringUtils.isBlank(mobile)) {
      throw new BusinessException(ResultCode.PHONE_MUST_NOT_EMPTY);
    }
    ValidateCode validateCode =
        (ValidateCode) redisTemplate.opsForValue().get(RedisKeyConstants.SMS_CODE_KEY + mobile);
    if (validateCode == null) {
      throw new BusinessException(ResultCode.SMS_CODE_NOT_EXITS);
    }
    if (validateCode.getExpried()) {
      redisTemplate.opsForValue().getOperations().delete(RedisKeyConstants.SMS_CODE_KEY + mobile);
      throw new BusinessException(ResultCode.SMS_CODE_IS_EXPRIED);
    }
    if (!validateCode.getCode().equals(smsCode)) {
      throw new BusinessException(ResultCode.SMS_CODE_ERROR);
    }
    redisTemplate.opsForValue().getOperations().delete(RedisKeyConstants.SMS_CODE_KEY + mobile);
  }
}
