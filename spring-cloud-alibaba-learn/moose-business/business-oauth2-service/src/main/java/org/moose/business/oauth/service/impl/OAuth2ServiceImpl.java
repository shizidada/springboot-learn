package org.moose.business.oauth.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.moose.business.oauth.service.OAuth2Service;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.RoleDTO;
import org.moose.provider.account.service.AccountService;
import org.moose.provider.account.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/30 20:53
 * @see org.moose.business.oauth.service.impl
 */
@Service
public class OAuth2ServiceImpl implements OAuth2Service {

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Reference(version = "1.0.0")
  private RoleService roleService;

  @Override public AccountDTO getAccountByPhone(String phone) {
    return accountService.getAccountByPhone(phone);
  }

  @Override public RoleDTO getAccountRole(Long accountId) {
    return roleService.getRoleByAccountId(accountId);
  }
}
