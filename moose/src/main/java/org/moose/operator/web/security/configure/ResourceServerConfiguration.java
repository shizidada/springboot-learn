package org.moose.operator.web.security.configure;

import javax.annotation.Resource;
import org.moose.operator.constant.PermitAllConstants;
import org.moose.operator.web.filter.LoginVerifyLimitFilter;
import org.moose.operator.web.filter.SmsVerifyLimitFilter;
import org.moose.operator.web.security.component.CustomAccessDeniedHandler;
import org.moose.operator.web.security.component.CustomAuthenticationEntryPoint;
import org.moose.operator.web.security.component.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author taohua
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Resource
  private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  @Resource
  private CustomAccessDeniedHandler customAccessDeniedHandler;

  @Resource
  private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Bean LoginVerifyLimitFilter loginLimitFilter() {
    return new LoginVerifyLimitFilter(redisTemplate, customAuthenticationFailureHandler);
  }

  @Bean
  SmsVerifyLimitFilter smsVerifyFilter() {
    return new SmsVerifyLimitFilter(redisTemplate, customAuthenticationFailureHandler);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

    // 对登录注册要允许匿名访问
    http
        .authorizeRequests()
        .antMatchers(
            PermitAllConstants.LOGIN_IN_URL,
            PermitAllConstants.LOGIN_OUT_URL,
            PermitAllConstants.LOGIN_STATUS_URL,
            PermitAllConstants.REGISTER_URL,
            PermitAllConstants.SEND_SMS_CODE_URL,
            PermitAllConstants.GET_REFRESH_TOKEN_URL,
            PermitAllConstants.REFRESH_TOKEN_URL,

            PermitAllConstants.DYNAMIC_RECOMMEND_LIST,
            PermitAllConstants.DYNAMIC_DETAIL
        ).permitAll();

    // add filter
    http
        .addFilterBefore(loginLimitFilter(), AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterBefore(smsVerifyFilter(), AbstractPreAuthenticatedProcessingFilter.class);

    // 禁用 session
    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests().anyRequest().authenticated()
        .and().cors()
        .and().csrf().disable();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    // 配置资源 ID
    resources.resourceId("app-resources").stateless(true);
    resources.accessDeniedHandler(customAccessDeniedHandler);
    resources.authenticationEntryPoint(customAuthenticationEntryPoint);
  }
}