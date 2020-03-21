package org.moose.account.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.account.mapper.AccountMapper;
import org.moose.account.model.domain.AccountDO;
import org.moose.account.model.domain.PasswordDO;
import org.moose.account.model.dto.AccountDTO;
import org.moose.account.model.dto.PasswordDTO;
import org.moose.account.service.AccountService;
import org.apache.dubbo.config.annotation.Service;
import org.moose.account.service.PasswordService;
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
 * @see org.moose.account.service.impl
 */
@Slf4j
@Service(version = "1.0.0")
@Transactional(rollbackFor = {Exception.class})
public class AccountServiceImpl implements AccountService {

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordService passwordService;

  @Override
  public int add(AccountDO accountDO) {
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
  public boolean add(AccountDO accountDO, PasswordDO passwordDO) {

    // add account
    int result = this.add(accountDO);

    // add password
    PasswordDTO passwordDTO = new PasswordDTO();
    BeanUtils.copyProperties(passwordDO, passwordDTO);
    int result2 = passwordService.add(passwordDTO);

    return result > 0 && result2 > 0;
  }
}
