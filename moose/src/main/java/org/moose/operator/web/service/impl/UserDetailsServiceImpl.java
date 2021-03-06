package org.moose.operator.web.service.impl;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.dto.PasswordDTO;
import org.moose.operator.web.security.component.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 21:22
 * @see org.moose.operator.web.service.impl
 */
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

    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResultCode.ACCOUNT_IS_EMPTY);
    }

    AccountDTO accountDTO = accountService.getByAccountName(accountName);
    if (ObjectUtils.isEmpty(accountDTO)) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    // TODO: 禁用账号如何防止多次请求，访问数据库 ？？？

    PasswordDTO passwordDTO = passwordService.getByAccountId(accountDTO.getAccountId());
    if (ObjectUtils.isEmpty(passwordDTO)) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    // TODO: 角色、权限集合
    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
    return new CustomUserDetails(accountDTO, passwordDTO, grantedAuthorities);
  }

  public UserDetails getAccountByPhone(String phone) {

    if (StringUtils.isBlank(phone)) {
      throw new BusinessException(ResultCode.PHONE_NUMBER_IS_EMPTY);
    }

    Object principal = accountService.getPrincipal();
    if (principal instanceof CustomUserDetails) {
      return (CustomUserDetails) principal;
    }

    AccountDTO accountDTO = accountService.getAccountByPhone(phone);
    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
    return new CustomUserDetails(accountDTO, null, grantedAuthorities);
  }
}
