package org.excel.operator.web.security;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.constants.SecurityConstants;
import org.excel.operator.web.security.filter.RedisTokenFilter;
import org.excel.operator.web.security.filter.SmsCodeFilter;
import org.excel.operator.web.security.sms.SmsCodeAuthenticationSecurityConfig;
import org.excel.operator.web.service.AccountService;
import org.excel.operator.web.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 21:20
 * @see org.excel.operator.configure
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

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

  @Override protected void configure(HttpSecurity http) throws Exception {

    // TODO:
    // ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();

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

        // 自定义登录
        .and()
        .formLogin()
        .loginProcessingUrl(SecurityConstants.LOGIN_IN_URL)
        .usernameParameter("accountName")
        .passwordParameter("password")
        // 登录成功
        .successHandler(customAuthenticationSuccessHandler)
        // 登录失败
        .failureHandler(customAuthenticationFailureHandler)

        // 自定义登出成功
        .and()
        .logout()
        // 自定义 url
        .logoutUrl(SecurityConstants.LOGIN_OUT_URL)
        // 自定义登出成功返回
        .logoutSuccessHandler(customLogoutSuccessHandler)
        // 清理 Session
        .invalidateHttpSession(true)

        .and()
        .authorizeRequests().anyRequest().authenticated()

        .and()
        .cors()

        // add filter
        .and()
        .addFilterBefore(smsCodeFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(redisTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    //.exceptionHandling().addObjectPostProcessor()

    http.apply(smsCodeAuthenticationSecurityConfig);

    http.csrf().disable();

    // 禁用 session
    //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  SmsCodeFilter smsCodeFilter() {
    return new SmsCodeFilter(customAuthenticationFailureHandler, redisTemplate);
  }

  @Bean
  RedisTokenFilter redisTokenFilter() {
    return new RedisTokenFilter(accountService, customAuthenticationFailureHandler);
  }

  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService())
        .passwordEncoder(passwordEncoder());
  }

  /**
   * 设置用户密码的加密方式
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 自定义UserDetailsService，授权
   */
  @Bean
  public UserDetailsService customUserDetailsService() {
    return new UserDetailsServiceImpl();
  }

  /**
   * AuthenticationManager
   *
   * @throws Exception
   */
  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
