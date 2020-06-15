package org.excel.operator.web.security.sms;

import org.excel.operator.web.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
 * @date 2020-06-15 23:21:23:21
 * @see org.excel.operator.web.security.sms
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
    smsCodeAuthenticationFilter.setAuthenticationManager(
        http.getSharedObject(AuthenticationManager.class));
    smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

    SmsCodeAuthenticationProvider smsCodeAuthenticationProvider =
        new SmsCodeAuthenticationProvider();
    smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

    http.authenticationProvider(smsCodeAuthenticationProvider)
        .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
