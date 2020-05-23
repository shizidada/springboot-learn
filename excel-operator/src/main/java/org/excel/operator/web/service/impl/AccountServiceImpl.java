package org.excel.operator.web.service.impl;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.component.SnowflakeIdWorker;
import org.excel.operator.domain.AccountDO;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.AccountMapper;
import org.excel.operator.web.service.AccountService;
import org.excel.operator.web.service.model.AccountModel;
import org.excel.operator.web.service.model.PasswordModel;
import org.excel.operator.web.service.model.RegisterInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.excel.operator.web.service.impl
 */

@Service
public class AccountServiceImpl implements AccountService {

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordServiceImpl passwordService;

  @Resource
  private PasswordEncoder passwordEncoder;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Override public AccountModel getAccountByAccountName(String accountName) {
    accountName = accountName.trim();
    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    if (accountDO == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    return this.convertAccountModelFromDataObject(accountDO);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override public void register(RegisterInfoModel registerInfoModel) {
    String accountName = registerInfoModel.getAccountName();
    String password = registerInfoModel.getPassword();
    String rePassword = registerInfoModel.getRePassword();

    if (!StringUtils.equals(password, rePassword)) {
      throw new BusinessException(ResultCode.RE_PASSWORD_ERROR);
    }

    AccountDO account = accountMapper.findByAccountName(accountName);
    if (account != null) {
      throw new BusinessException(ResultCode.ACCOUNT_NAME_EXITS);
    }

    AccountDO accountDO = this.convertAccountDoFromModel(registerInfoModel);
    PasswordModel passwordModel = this.convertPasswordModelFromRegisterInfoModel(registerInfoModel);
    accountMapper.insertAccount(accountDO);

    passwordModel.setPasswordId(snowflakeIdWorker.nextId());
    passwordModel.setAccountId(accountDO.getAccountId());
    passwordService.insertPassword(passwordModel);
  }

  private AccountModel convertAccountModelFromDataObject(AccountDO accountDO) {
    AccountModel accountModel = new AccountModel();
    BeanUtils.copyProperties(accountDO, accountModel);
    return accountModel;
  }

  private PasswordModel convertPasswordModelFromRegisterInfoModel(
      RegisterInfoModel registerInfoModel) {
    PasswordModel passwordModel = new PasswordModel();
    String password = passwordEncoder.encode(registerInfoModel.getPassword());
    passwordModel.setPassword(password);
    return passwordModel;
  }

  private AccountDO convertAccountDoFromModel(RegisterInfoModel registerInfoModel) {
    AccountDO accountDO = new AccountDO();
    accountDO.setAccountId(snowflakeIdWorker.nextId());
    accountDO.setAccountName(registerInfoModel.getAccountName());
    return accountDO;
  }
}
