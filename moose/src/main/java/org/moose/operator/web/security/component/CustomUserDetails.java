package org.moose.operator.web.security.component;

import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.dto.PasswordDTO;
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
 * @see org.moose.operator.model.dto
 */
public class CustomUserDetails implements UserDetails {

  public static final String CAN_USE = "1";

  private AccountDTO accountDTO;

  private PasswordDTO passwordDTO;

  /**
   * 权限集合
   */
  private List<GrantedAuthority> authorities;

  public CustomUserDetails() {
  }

  public CustomUserDetails(AccountDTO accountDTO,
      PasswordDTO passwordDTO,
      List<GrantedAuthority> authorities) {
    this.accountDTO = accountDTO;
    this.passwordDTO = passwordDTO;
    this.authorities = authorities;
  }

  public AccountDTO getAccountDTO() {
    return accountDTO;
  }

  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override public String getPassword() {
    return passwordDTO.getPassword();
  }

  @Override public String getUsername() {
    return accountDTO.getAccountName();
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
    return StringUtils.equals(accountDTO.getStatus(), CAN_USE);
  }
}
