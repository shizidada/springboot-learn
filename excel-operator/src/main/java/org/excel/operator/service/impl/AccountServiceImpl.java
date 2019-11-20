package org.excel.operator.service.impl;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResponseCode;
import org.excel.operator.entity.AccountDO;
import org.excel.operator.entity.PasswordDO;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.AccountMapper;
import org.excel.operator.service.AccountService;
import org.excel.operator.service.model.AccountModel;
import org.excel.operator.service.model.PasswordModel;
import org.excel.operator.service.model.RegisterInfoModel;
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
 * @see org.excel.operator.service.impl
 */

@Service
public class AccountServiceImpl implements AccountService {

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordServiceImpl passwordService;

  @Resource
  private PasswordEncoder passwordEncoder;

  @Override public AccountModel getAccountByAccountName(String accountName) {
    accountName = accountName.trim();
    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResponseCode.ACCOUNT_NOT_NULL.getMessage(),
          ResponseCode.ACCOUNT_NOT_NULL.getCode());
    }

    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    if (accountDO == null) {
      throw new BusinessException(ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getMessage(),
          ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getCode());
    }

    return this.convertAccountModelFromDataObject(accountDO);
  }

  @Override public void login(String accountName, String password) {
    accountName = accountName.trim();
    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResponseCode.ACCOUNT_NOT_NULL.getMessage(),
          ResponseCode.ACCOUNT_NOT_NULL.getCode());
    }

    password = password.trim();
    if (StringUtils.isEmpty(password)) {
      throw new BusinessException(ResponseCode.PASSWORD_NOT_NULL.getMessage(),
          ResponseCode.PASSWORD_NOT_NULL.getCode());
    }

    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    if (accountDO == null) {
      throw new BusinessException(ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getMessage(),
          ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getCode());
    }

    PasswordModel passwordModel = passwordService.findByAccountId(accountDO.getAccountId());
    if (passwordModel == null) {
      throw new BusinessException(ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getMessage(),
          ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getCode());
    }

    if (!StringUtils.equals(password, passwordModel.getPassword())) {
      throw new BusinessException(ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getMessage(),
          ResponseCode.ACCOUNT_OR_PASSWORD_ERROR.getCode());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override public void register(RegisterInfoModel registerInfoModel) {
    String accountName = registerInfoModel.getAccountName();
    String password = registerInfoModel.getPassword();
    String rePassword = registerInfoModel.getRePassword();

    if (!StringUtils.equals(password, rePassword)) {
      throw new BusinessException(ResponseCode.RE_PASSWORD_ERROR.getMessage(),
          ResponseCode.RE_PASSWORD_ERROR.getCode());
    }

    AccountDO account = accountMapper.findByAccountName(accountName);
    if (account != null) {
      throw new BusinessException(ResponseCode.ACCOUNT_NAME_EXITS.getMessage(),
          ResponseCode.ACCOUNT_NAME_EXITS.getCode());
    }

    AccountDO accountDO = this.convertAccountDoFromModel(registerInfoModel);
    PasswordModel passwordModel = this.convertPasswordModelFromRegisterInfoModel(registerInfoModel);
    accountMapper.insertAccount(accountDO);

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
    accountDO.setAccountName(registerInfoModel.getAccountName());
    return accountDO;
  }
}
