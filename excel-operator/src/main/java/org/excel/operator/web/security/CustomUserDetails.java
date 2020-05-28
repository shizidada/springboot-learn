package org.excel.operator.web.security;

import java.util.Collection;
import java.util.List;
import org.excel.operator.web.service.model.AccountModel;
import org.excel.operator.web.service.model.PasswordModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 22:04
 * @see org.excel.operator.web.service.model
 */
public class CustomUserDetails implements UserDetails {

  private AccountModel accountModel;

  private PasswordModel passwordModel;

  /**
   * 权限集合
   */
  private List<GrantedAuthority> authorities;

  public CustomUserDetails() {
  }

  public CustomUserDetails(AccountModel accountModel,
      PasswordModel passwordModel,
      List<GrantedAuthority> authorities) {
    this.accountModel = accountModel;
    this.passwordModel = passwordModel;
    this.authorities = authorities;
  }

  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override public String getPassword() {
    return passwordModel.getPassword();
  }

  @Override public String getUsername() {
    return accountModel.getAccountName();
  }

  @Override public boolean isAccountNonExpired() {
    return true;
  }

  @Override public boolean isAccountNonLocked() {
    return true;
  }

  @Override public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override public boolean isEnabled() {
    return true;
  }
}
