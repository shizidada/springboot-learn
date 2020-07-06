package org.moose.business.oauth.configure.sms;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-30 15:06:15:06
 * @see org.moose.business.oauth.configure.sms
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {
  /**
   * 手机号
   */
  private final Object principal;

  public SmsCodeAuthenticationToken(Object mobile) {
    super(null);
    this.principal = mobile;
    setAuthenticated(false);
  }

  public SmsCodeAuthenticationToken(Object principal,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    // must use super, as we override
    super.setAuthenticated(true);
  }

  // ~ Methods
  // ========================================================================================================

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }

    super.setAuthenticated(false);
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
  }
}
