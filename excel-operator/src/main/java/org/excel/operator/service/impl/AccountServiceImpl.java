package org.excel.operator.service.impl;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResponseCode;
import org.excel.operator.entity.AccountDO;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.AccountMapper;
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

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordServiceImpl passwordService;

  @Override public void login(String accountName, String password) {
    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResponseCode.ACCOUNT_NOT_NULL.getMessage(),
          ResponseCode.ACCOUNT_NOT_NULL.getCode());
    }

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
}
