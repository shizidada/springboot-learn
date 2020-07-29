package org.excel.operator.web.security.configure;

import javax.annotation.Resource;
import org.excel.operator.constants.SecurityConstants;
import org.excel.operator.web.security.CustomAccessDeniedHandler;
import org.excel.operator.web.security.CustomAuthenticationEntryPoint;
import org.excel.operator.web.security.CustomAuthenticationFailureHandler;
import org.excel.operator.web.security.CustomAuthenticationSuccessHandler;
import org.excel.operator.web.security.CustomLogoutSuccessHandler;
import org.excel.operator.web.security.filter.LoginFailCountFilter;
import org.excel.operator.web.security.filter.RedisTokenFilter;
import org.excel.operator.web.security.filter.SmsCodeFilter;
import org.excel.operator.web.security.sms.SmsCodeAuthenticationSecurityConfig;
import org.excel.operator.web.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Resource
  private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  @Resource
  private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  @Resource
  private CustomLogoutSuccessHandler customLogoutSuccessHandler;

  @Resource
  private CustomAccessDeniedHandler customAccessDeniedHandler;

  @Resource
  private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Resource
  private AccountService accountService;

  @Resource
  private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // TODO:
    // ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();

    http.apply(smsCodeAuthenticationSecurityConfig);

    http.authorizeRequests()
        .antMatchers(HttpMethod.GET,
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/swagger-resources/**",
            "/v2/api-docs/**"
        ).permitAll()
        // druid 数据库监控
        .antMatchers("/druid/**").permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

        // 对登录注册要允许匿名访问
        .and()
        .authorizeRequests().antMatchers(
        SecurityConstants.LOGIN_IN_URL,
        SecurityConstants.LOGIN_OUT_URL,
        SecurityConstants.LOGIN_STATUS_URL,
        SecurityConstants.REGISTER_URL,
        SecurityConstants.SMS_LOGIN_URL,
        SecurityConstants.SEND_SMS_CODE_URL,

        // for test
        "/api/v1/excel/**",

        "/ws/*",

        "/friends/*").permitAll()

        .and()
        .exceptionHandling()
        .accessDeniedHandler(customAccessDeniedHandler)
        .authenticationEntryPoint(customAuthenticationEntryPoint)

        //自定义登录
        .and()
        .formLogin()
        .loginProcessingUrl(SecurityConstants.LOGIN_IN_URL)
        .usernameParameter(SecurityConstants.LOGIN_USERNAME_PARAMETER)
        .passwordParameter(SecurityConstants.LOGIN_PASSWORD_PARAMETER)
        // 登录成功
        .successHandler(customAuthenticationSuccessHandler)
        // 登录失败
        .failureHandler(customAuthenticationFailureHandler)

        //自定义登出成功
        .and()
        .logout()
        // 自定义 url
        .logoutUrl(SecurityConstants.LOGIN_OUT_URL)
        // 自定义登出成功返回
        .logoutSuccessHandler(customLogoutSuccessHandler)
        // 清理 Session
        .invalidateHttpSession(true)

        //add filter
        .and()
        .addFilterBefore(smsCodeFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(redisTokenFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(loginFailCountFilter(), UsernamePasswordAuthenticationFilter.class)

        .csrf()
        .disable()
        .exceptionHandling()

        // 不保存 Session
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeRequests()
        .anyRequest().authenticated();
  }

  @Bean
  SmsCodeFilter smsCodeFilter() {
    return new SmsCodeFilter(customAuthenticationFailureHandler, redisTemplate);
  }

  @Bean
  RedisTokenFilter redisTokenFilter() {
    return new RedisTokenFilter(customAuthenticationFailureHandler, accountService);
  }

  @Bean LoginFailCountFilter loginFailCountFilter() {
    return new LoginFailCountFilter(customAuthenticationFailureHandler, redisTemplate);
  }
}