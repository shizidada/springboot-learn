package org.moose.business.oauth.service.impl;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.dubbo.config.annotation.Reference;
import org.moose.business.oauth.configure.CustomOAuth2Exception;
import org.moose.business.oauth.model.dto.OAuth2UserDetails;
import org.moose.commons.base.code.AccountCode;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.model.dto.RoleDTO;
import org.moose.provider.account.service.AccountService;
import org.moose.provider.account.service.PasswordService;
import org.moose.provider.account.service.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-13 12:51:12:51
 * @see org.moose.business.oauth.service
 */
public class UserDetailsServiceImpl implements UserDetailsService {

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Reference(version = "1.0.0")
  private PasswordService passwordService;

  @Reference(version = "1.0.0")
  private RoleService roleService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    AccountDTO accountDTO = accountService.getAccountByAccountName(username);
    if (accountDTO == null) {
      throw new CustomOAuth2Exception(AccountCode.ACCOUNT_PASSWORD_ERROR.getMessage(),
          AccountCode.ACCOUNT_PASSWORD_ERROR.getCode());
    }

    PasswordDTO passwordDTO = passwordService.get(accountDTO.getAccountId());
    if (passwordDTO == null) {
      throw new CustomOAuth2Exception(AccountCode.ACCOUNT_PASSWORD_ERROR.getMessage(),
          AccountCode.ACCOUNT_PASSWORD_ERROR.getCode());
    }

    RoleDTO role = roleService.getRoleByAccountId(accountDTO.getAccountId());

    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
    return new OAuth2UserDetails(accountDTO, passwordDTO, grantedAuthorities);
  }
}
