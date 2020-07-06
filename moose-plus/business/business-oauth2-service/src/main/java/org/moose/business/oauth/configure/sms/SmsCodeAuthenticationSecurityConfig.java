package org.moose.business.oauth.configure.sms;

import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-30 15:17:15:17
 * @see org.moose.business.oauth.configure.sms
 */

@Component
public class SmsCodeAuthenticationSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Resource
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Resource
  private AuthenticationFailureHandler authenticationFailureHandler;

  @Resource
  private UserDetailsService userDetailsService;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    SmsCodeAuthenticationFilter smsCodeAuthenticationFilter =
        new SmsCodeAuthenticationFilter();
    smsCodeAuthenticationFilter.setAuthenticationManager(
        http.getSharedObject(AuthenticationManager.class));
    smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

    SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);

    // 将SmsCodeAuthenticationFilter放到过滤器链的UsernamePasswordAuthenticationFilter的后面
    http
        .authenticationProvider(provider)
        .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
