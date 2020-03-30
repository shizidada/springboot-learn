package org.moose.business.oauth.model.dto;

import java.util.Collection;
import java.util.List;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/30 22:00
 * @see org.moose.business.oauth.model.dto
 */
public class OAuth2UserDetails implements UserDetails {

  private AccountDTO accountDTO;

  private PasswordDTO passwordDTO;

  private List<GrantedAuthority> grantedAuthorities;

  public OAuth2UserDetails(AccountDTO accountDTO,
      PasswordDTO passwordDTO, List<GrantedAuthority> grantedAuthorities) {
    this.accountDTO = accountDTO;
    this.passwordDTO = passwordDTO;
    this.grantedAuthorities = grantedAuthorities;
  }

  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
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
    return true;
  }
}
