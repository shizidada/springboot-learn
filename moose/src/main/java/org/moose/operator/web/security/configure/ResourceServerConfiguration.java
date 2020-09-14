package org.moose.operator.web.security.configure;

import javax.annotation.Resource;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.web.security.component.CustomAccessDeniedHandler;
import org.moose.operator.web.security.component.CustomAuthenticationEntryPoint;
import org.moose.operator.web.security.component.CustomAuthenticationFailureHandler;
import org.moose.operator.web.security.component.CustomLogoutSuccessHandler;
import org.moose.operator.web.security.filter.LoginLimitFilter;
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
  private CustomLogoutSuccessHandler customLogoutSuccessHandler;

  @Resource
  private CustomAccessDeniedHandler customAccessDeniedHandler;

  @Resource
  private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Bean LoginLimitFilter loginLimitFilter() {
    return new LoginLimitFilter(redisTemplate, customAuthenticationFailureHandler);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

    // 对登录注册要允许匿名访问
    http
        .authorizeRequests()
        .antMatchers(
            SecurityConstants.LOGIN_IN_URL,
            SecurityConstants.LOGIN_OUT_URL,
            SecurityConstants.LOGIN_STATUS_URL,
            SecurityConstants.REGISTER_URL,
            SecurityConstants.SEND_SMS_CODE_URL,
            SecurityConstants.GET_REFRESH_TOKEN_URL,
            SecurityConstants.REFRESH_TOKEN_URL
        ).permitAll();

    http
        .exceptionHandling()
        .accessDeniedHandler(customAccessDeniedHandler)
        .authenticationEntryPoint(customAuthenticationEntryPoint);

    // add filter
    http
        .addFilterBefore(loginLimitFilter(), AbstractPreAuthenticatedProcessingFilter.class);

    // 禁用 session
    http
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .csrf()
        .disable()
        .exceptionHandling()

        .and()
        .authorizeRequests().anyRequest().authenticated();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    // 配置资源 ID
    resources.resourceId("app-resources").stateless(true);
  }
}