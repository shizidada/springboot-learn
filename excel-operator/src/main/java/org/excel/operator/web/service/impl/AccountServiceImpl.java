package org.excel.operator.web.service.impl;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.component.SnowflakeIdWorker;
import org.excel.operator.domain.AccountDO;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.AccountMapper;
import org.excel.operator.web.security.CustomUserDetails;
import org.excel.operator.web.service.AccountService;
import org.excel.operator.web.service.model.AccountModel;
import org.excel.operator.web.service.model.PasswordModel;
import org.excel.operator.web.service.model.RegisterInfoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @Override public AccountModel getByAccountName(String accountName) {
    accountName = accountName.trim();
    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    if (accountDO == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    AccountModel accountModel = new AccountModel();
    BeanUtils.copyProperties(accountDO, accountModel);
    return accountModel;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override public void register(RegisterInfoModel registerInfo) {
    String password = registerInfo.getPassword();
    String rePassword = registerInfo.getRePassword();

    // 比对两次密码是否一致
    if (!StringUtils.equals(password, rePassword)) {
      throw new BusinessException(ResultCode.SECOND_PASSWORD_ERROR);
    }

    // 查询对应 账号名称
    String accountName = registerInfo.getAccountName();
    AccountDO account = accountMapper.findByAccountName(accountName);
    if (account != null) {
      throw new BusinessException(ResultCode.ACCOUNT_NAME_EXITS);
    }

    // 查询手机号是否存在
    String phone = registerInfo.getPhone();
    account = accountMapper.findByPhone(phone);
    if (account != null) {
      throw new BusinessException(ResultCode.PHONE_EXITS);
    }

    AccountDO accountDO = new AccountDO();
    accountDO.setAccountId(snowflakeIdWorker.nextId());
    accountDO.setAccountName(registerInfo.getAccountName());
    accountDO.setPhone(registerInfo.getPhone());
    accountDO.setAvatar(registerInfo.getAvatar());
    accountMapper.insertAccount(accountDO);

    PasswordModel passwordModel = new PasswordModel();
    passwordModel.setAccountId(accountDO.getAccountId());
    passwordModel.setPasswordId(snowflakeIdWorker.nextId());
    // 加密密码
    passwordModel.setPassword(passwordEncoder.encode(registerInfo.getPassword()));
    passwordService.addPassword(passwordModel);
  }

  @Override
  public boolean isLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return false;
    }
    Object principal = authentication.getPrincipal();
    return principal instanceof CustomUserDetails;
  }

  @Override public Object getPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      return authentication.getPrincipal();
    }
    return null;
  }
}
