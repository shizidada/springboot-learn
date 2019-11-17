package org.excel.operator.service.impl;

import javax.annotation.Resource;
import org.excel.operator.entity.AccountDO;
import org.excel.operator.mapper.AccountMapper;
import org.excel.operator.mapper.PasswordMapper;
import org.excel.operator.service.AccountService;
import org.excel.operator.service.model.PasswordModel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.excel.operator.service.impl
 */

@Service
public class AccountServiceImpl implements AccountService {

  @Resource AccountMapper accountMapper;

  @Resource PasswordServiceImpl passwordService;

  @Override public boolean login(String accountName, String password) {
    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    if (accountDO != null) {
      PasswordModel passwordModel = passwordService.findByAccountId(accountDO.getAccountId());
      if (passwordModel != null) {
        if (passwordModel.getPassword().equals(password)) {
          return true;
        }
      }
    }
    return false;
  }
}
