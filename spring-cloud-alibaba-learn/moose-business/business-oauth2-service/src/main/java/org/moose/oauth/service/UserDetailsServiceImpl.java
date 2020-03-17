package org.moose.oauth.service;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.dubbo.config.annotation.Reference;
import org.moose.account.model.dto.AccountDTO;
import org.moose.account.model.dto.PasswordDTO;
import org.moose.account.service.AccountService;
import org.moose.account.service.PasswordService;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-13 12:51:12:51
 * @see org.moose.oauth.service
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final String USERNAME = "admin";
  private static final String PASSWORD =
      "$2a$10$YNUV/BtCel2orbhgrxyPJeljdKVty6yTAL.Cj4v3XhwHWXBkgyPYW";

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Reference(version = "1.0.0")
  private PasswordService passwordService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    AccountDTO accountDTO = accountService.get(username);
    if (accountDTO == null) {
      throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_ERROR.getMessage(),
          ResultCode.ACCOUNT_PASSWORD_ERROR.getCode());
    }

    PasswordDTO passwordDTO = passwordService.get(accountDTO.getAccountId());
    if (passwordDTO == null) {
      throw new BusinessException(ResultCode.ACCOUNT_PASSWORD_ERROR.getMessage(),
          ResultCode.ACCOUNT_PASSWORD_ERROR.getCode());
    }

    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
    grantedAuthorities.add(grantedAuthority);

    String accountName = accountDTO.getAccountName();

    String password = passwordDTO.getPassword();

    return new User(accountName, password, grantedAuthorities);
  }
}
