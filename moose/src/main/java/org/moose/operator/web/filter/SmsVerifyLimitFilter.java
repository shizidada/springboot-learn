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
import org.moose.operator.constant.PermitAllConstants;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.model.emun.SmsCodeEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author taohua
 */
@Slf4j
public class SmsVerifyLimitFilter extends OncePerRequestFilter {

  /**
   * 存放所有需要校验验证码的url
   */
  private final Map<String, String> urlMap = new HashMap<>();

  private RedisTemplate<String, Object> redisTemplate;

  private AuthenticationFailureHandler authenticationFailureHandler;

  public SmsVerifyLimitFilter(RedisTemplate<String, Object> redisTemplate,
      AuthenticationFailureHandler authenticationFailureHandler) {
    this.redisTemplate = redisTemplate;
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  @Override public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();

    addUrlToMap(PermitAllConstants.SEND_SMS_CODE_URL, DefaultConstants.DEFAULT_PARAMETER_NAME_PHONE);
  }

  protected void addUrlToMap(String urlString, String smsParam) {
    if (StringUtils.isNotBlank(urlString)) {
      String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
      for (String url : urls) {
        urlMap.put(url, smsParam);
      }
    }
  }

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
   * 短信发送校验
   *
   * TODO: 短信提交校验
   *
   * @param request HttpServletRequest
   */
  private void validate(HttpServletRequest request) {
    String smsType = request.getParameter(DefaultConstants.DEFAULT_PARAMETER_NAME_SMS_TYPE);
    if (!SmsCodeEnum.isExist(smsType)) {
      throw new BusinessException(ResultCode.SMS_TYPE_ERROR);
    }

    String phoneNumber = request.getParameter(DefaultConstants.DEFAULT_PARAMETER_NAME_PHONE);

    // 计算发送次数
    String smsSendCountKey = RedisKeyConstants.SMS_PHONE_KEY + phoneNumber;
    Integer sendCount = (Integer) redisTemplate.opsForValue().get(smsSendCountKey);
    if (ObjectUtils.isEmpty(sendCount)) {
      redisTemplate.opsForValue()
          .set(smsSendCountKey, 1, SecurityConstants.SMS_TIME_OF_DAY, TimeUnit.SECONDS);
    } else {
      // 判断手机号时间范围内，累计发送次数
      if (sendCount >= SecurityConstants.MAX_COUNT_OF_DAY) {
        throw new BusinessException(ResultCode.SMS_CODE_COUNT);
      }
      redisTemplate.opsForValue().increment(smsSendCountKey);
      redisTemplate.expire(smsSendCountKey, SecurityConstants.SMS_TIME_OF_DAY, TimeUnit.SECONDS);
    }
  }
}
