package org.excel.operator.service.impl;

import javax.annotation.Resource;

import org.excel.operator.common.api.ResultCode;
import org.excel.operator.entity.PasswordDO;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.PasswordMapper;
import org.excel.operator.service.PasswordService;
import org.excel.operator.service.model.PasswordModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 20:55
 * @see org.excel.operator.service.impl
 */

@Service
public class PasswordServiceImpl implements PasswordService {

  @Resource
  private PasswordMapper passwordMapper;

  @Override public PasswordModel findByAccountId(Long accountId) {
    PasswordDO passwordDO = passwordMapper.findByAccountId(accountId);
    if (passwordDO == null) {
      throw new BusinessException(ResultCode.PASSWORD_NOT_NULL.getMessage(),
          ResultCode.PASSWORD_NOT_NULL.getCode());
    }
    return this.convertPasswordModelFormDataObject(passwordDO);
  }

  @Override public PasswordModel insertPassword(PasswordModel passwordModel) {
    if (passwordModel == null) {
      throw new BusinessException(ResultCode.REGISTER_FAIL.getMessage(),
          ResultCode.REGISTER_FAIL.getCode());
    }
    PasswordDO passwordDO = this.convertPasswordModelToDataObject(passwordModel);
    passwordMapper.insertPassword(passwordDO);
    return passwordModel;
  }

  private PasswordDO convertPasswordModelToDataObject(PasswordModel passwordModel) {
    PasswordDO passwordDO = new PasswordDO();
    BeanUtils.copyProperties(passwordModel, passwordDO);
    return passwordDO;
  }

  private PasswordModel convertPasswordModelFormDataObject(PasswordDO passwordDO) {
    PasswordModel passwordModel = new PasswordModel();
    BeanUtils.copyProperties(passwordDO, passwordModel);
    return passwordModel;
  }
}
