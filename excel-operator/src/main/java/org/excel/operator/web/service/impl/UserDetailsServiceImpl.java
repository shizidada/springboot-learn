package org.excel.operator.web.service.impl;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.web.security.CustomUserDetails;
import org.excel.operator.web.service.model.AccountModel;
import org.excel.operator.web.service.model.PasswordModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
 * @see org.excel.operator.web.service.impl
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Resource
  private AccountServiceImpl accountService;

  @Resource
  private PasswordServiceImpl passwordService;

  /**
   * Spring Security
   *
   * @param accountName 账号
   * @return 是否成功
   * @throws UsernameNotFoundException 用户名密码异常
   */
  @Override
  public UserDetails loadUserByUsername(String accountName)
      throws UsernameNotFoundException {

    Object principal = accountService.getPrincipal();
    if (principal instanceof CustomUserDetails) {
      return (CustomUserDetails) principal;
    }

    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResultCode.ACCOUNT_NOT_EMPTY);
    }

    AccountModel accountModel = accountService.getByAccountName(accountName);
    if (accountModel == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    // TODO: 禁用账号如何防止多次请求，访问数据库 ？？？

    PasswordModel passwordModel = passwordService.getByAccountId(accountModel.getAccountId());
    if (passwordModel == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    // TODO: 角色、权限集合
    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
    return new CustomUserDetails(accountModel, passwordModel, grantedAuthorities);
  }
}
