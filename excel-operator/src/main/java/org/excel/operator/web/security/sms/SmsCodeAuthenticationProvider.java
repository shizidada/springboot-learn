package org.excel.operator.web.security.sms;

import org.excel.operator.common.api.ResultCode;
import org.excel.operator.web.security.component.CustomUserDetails;
import org.excel.operator.web.service.impl.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-15 23:20:23:20
 * @see org.excel.operator.web.security.sms
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

  private UserDetailsServiceImpl userDetailsService;

  public UserDetailsService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * 进行身份认证的逻辑
   *
   * @param authentication 就是我们传入的Token
   * @return
   * @throws AuthenticationException
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    // 利用UserDetailsService获取用户信息，拿到用户信息后重新组装一个已认证的Authentication
    SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

    /**
     * 根据手机号码拿到用户信息
     */
    String phone = (String) authenticationToken.getPrincipal();

    CustomUserDetails customUserDetails =
        (CustomUserDetails) userDetailsService.getAccountByPhone(phone);
    if (customUserDetails == null) {
      throw new InternalAuthenticationServiceException(ResultCode.PHONE_NOT_EXITS.getMessage());
    }
    SmsCodeAuthenticationToken smsCodeAuthenticationToken =
        new SmsCodeAuthenticationToken(customUserDetails, customUserDetails.getAuthorities());
    smsCodeAuthenticationToken.setDetails(authenticationToken.getDetails());
    return smsCodeAuthenticationToken;
  }

  /**
   * AuthenticationManager挑选一个 AuthenticationProvider来处理传入进来的Token就是根据supports方法来判断的
   *
   * @param clazz
   * @return
   */
  @Override
  public boolean supports(Class<?> clazz) {
    // 判断出入进来的是不是 SmsCodeAuthenticationToken 类型
    return SmsCodeAuthenticationToken.class.isAssignableFrom(clazz);
  }
}
