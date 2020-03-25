package org.moose.provider.account.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.provider.account.mapper.AccountMapper;
import org.moose.provider.account.model.domain.AccountDO;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.model.dto.RoleDTO;
import org.moose.provider.account.service.AccountService;
import org.apache.dubbo.config.annotation.Service;
import org.moose.provider.account.service.PasswordService;
import org.moose.provider.account.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 23:38
 * @see org.moose.provider.account.service.impl
 */
@Slf4j
@Service(version = "1.0.0")
@Transactional(rollbackFor = {Exception.class})
public class AccountServiceImpl implements AccountService {

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordService passwordService;

  @Resource
  private RoleService roleService;

  @Override
  public int add(AccountDTO accountDTO) {
    AccountDO accountDO = new AccountDO();
    BeanUtils.copyProperties(accountDTO, accountDO);
    return accountMapper.insert(accountDO);
  }

  @Override
  public AccountDTO get(String accountName) {
    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    if (accountDO == null) {
      return null;
    }
    AccountDTO accountDTO = new AccountDTO();
    BeanUtils.copyProperties(accountDO, accountDTO);
    return accountDTO;
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public boolean add(AccountDTO accountDTO, PasswordDTO passwordDTO, RoleDTO roleDTO) {

    // add account
    int result = this.add(accountDTO);

    // add password
    int result2 = passwordService.add(passwordDTO);

    // add role
    int result3 = roleService.add(roleDTO);
    return result > 0 && result2 > 0 && result3 > 0;
  }
}
