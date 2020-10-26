package org.moose.operator.web.security.configure;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import org.moose.operator.constant.PermitAllConstants;
import org.moose.operator.web.filter.CheckHeaderTokenFilter;
import org.moose.operator.web.filter.LoginVerifyLimitFilter;
import org.moose.operator.web.filter.SmsVerifyLimitFilter;
import org.moose.operator.web.security.component.CustomAccessDeniedHandler;
import org.moose.operator.web.security.component.CustomAuthenticationEntryPoint;
import org.moose.operator.web.security.component.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author taohua
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter implements
    InitializingBean {

  private final List<String> anonymousAntPatterns = new ArrayList<>();

  @Resource
  private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  @Resource
  private CustomAccessDeniedHandler customAccessDeniedHandler;

  @Resource
  private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Lazy
  @Resource
  private TokenStore tokenStore;

  @Override public void afterPropertiesSet() throws ServletException {
    anonymousAntPatterns.add(PermitAllConstants.LOGIN_IN_URL);
    anonymousAntPatterns.add(PermitAllConstants.LOGIN_OUT_URL);
    anonymousAntPatterns.add(PermitAllConstants.REGISTER_URL);
    anonymousAntPatterns.add(PermitAllConstants.LOGIN_STATUS_URL);
    anonymousAntPatterns.add(PermitAllConstants.GET_REFRESH_TOKEN_URL);
    anonymousAntPatterns.add(PermitAllConstants.REFRESH_TOKEN_URL);
    anonymousAntPatterns.add(PermitAllConstants.SEND_SMS_CODE_URL);
    anonymousAntPatterns.add(PermitAllConstants.DYNAMIC_RECOMMEND_LIST);
    anonymousAntPatterns.add(PermitAllConstants.DYNAMIC_DETAIL);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

    // 对登录注册要允许匿名访问
    for (String pattern : anonymousAntPatterns) {
      http.authorizeRequests().antMatchers(pattern).permitAll();
    }

    // add filter
    http
        .addFilterBefore(new CheckHeaderTokenFilter(tokenStore, anonymousAntPatterns),
            AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterBefore(
            new LoginVerifyLimitFilter(redisTemplate, customAuthenticationFailureHandler),
            AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterBefore(
            new SmsVerifyLimitFilter(redisTemplate, customAuthenticationFailureHandler),
            AbstractPreAuthenticatedProcessingFilter.class);

    // 禁用 session
    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests().anyRequest().authenticated().and()
        .cors().and()
        .csrf().disable();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    // 配置资源 ID
    resources.resourceId("app-resources").stateless(true);
    resources.accessDeniedHandler(customAccessDeniedHandler);
    resources.authenticationEntryPoint(customAuthenticationEntryPoint);
  }
}