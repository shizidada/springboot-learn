package org.excel.operator.security;

import org.excel.operator.common.api.ResultCode;
import org.excel.operator.service.model.AccountModel;
import org.excel.operator.service.model.PasswordModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.service.impl.AccountServiceImpl;
import org.excel.operator.service.impl.PasswordServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 21:22
 * @see org.excel.operator.service.impl
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Resource
  private AccountServiceImpl accountService;

  @Resource
  private PasswordServiceImpl passwordService;

  @Override public UserDetails loadUserByUsername(String accountName)
      throws UsernameNotFoundException {
    AccountModel accountModel = accountService.getAccountByAccountName(accountName);
    if (accountModel == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR.getMessage(),
          ResultCode.ACCOUNT_OR_PASSWORD_ERROR.getCode());
    }

    PasswordModel passwordModel = passwordService.findByAccountId(accountModel.getAccountId());
    if (passwordModel == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR.getMessage(),
          ResultCode.ACCOUNT_OR_PASSWORD_ERROR.getCode());
    }
    // 权限集合
    List<GrantedAuthority> authorities = new ArrayList<>();
    return new CustomUserDetails(accountModel, passwordModel, authorities);
  }
}
