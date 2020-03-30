package org.moose.business.oauth.configure.sms;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-30 15:13:15:13
 * @see org.moose.business.oauth.configure.sms
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

  private UserDetailsService userDetailsService;

  public UserDetailsService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {

    SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
    //调用自定义的userDetailsService认证
    UserDetails user =
        userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

    if (user == null) {
      throw new InternalAuthenticationServiceException("无法获取用户信息");
    }
    // 如果 user 不为空重新构建 SmsCodeAuthenticationToken（已认证）
    SmsCodeAuthenticationToken authenticationResult =
        new SmsCodeAuthenticationToken(user, user.getAuthorities());

    authenticationResult.setDetails(authenticationToken.getDetails());

    return authenticationResult;
  }

  /**
   * 只有Authentication为SmsCodeAuthenticationToken使用此Provider认证
   *
   * @param authentication
   * @return
   */
  @Override public boolean supports(Class<?> authentication) {
    return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
