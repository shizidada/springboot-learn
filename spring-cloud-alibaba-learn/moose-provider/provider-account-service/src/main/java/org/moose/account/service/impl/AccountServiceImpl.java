package org.moose.account.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.account.mapper.AccountMapper;
import org.moose.account.model.domain.AccountDO;
import org.moose.account.model.dto.AccountDTO;
import org.moose.account.service.AccountService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

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
@Service(version = "1.0.0")
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Resource
  private AccountMapper accountMapper;

  @Override
  public int add(AccountDO accountDO) {
    return 0;
  }

  @Override
  public AccountDTO get(String accountName) {
    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    AccountDTO accountDTO = new AccountDTO();
    BeanUtils.copyProperties(accountDO, accountDTO);
    return accountDTO;
  }
}
