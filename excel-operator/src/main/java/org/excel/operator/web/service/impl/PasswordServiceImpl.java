package org.excel.operator.web.service.impl;

import javax.annotation.Resource;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.domain.PasswordDO;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.PasswordMapper;
import org.excel.operator.web.service.PasswordService;
import org.excel.operator.web.service.model.PasswordModel;
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
 * @see org.excel.operator.web.service.impl
 */

@Service
public class PasswordServiceImpl implements PasswordService {

  @Resource
  private PasswordMapper passwordMapper;

  @Override public PasswordModel getByAccountId(Long accountId) {
    PasswordDO passwordDO = passwordMapper.findByAccountId(accountId);
    if (passwordDO == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }
    PasswordModel passwordModel = new PasswordModel();
    BeanUtils.copyProperties(passwordDO, passwordModel);
    return passwordModel;
  }

  /**
   * 插入密码
   * @param passwordModel 密码
   * @return
   */
  @Override
  public PasswordModel addPassword(PasswordModel passwordModel) {
    if (passwordModel == null) {
      throw new BusinessException(ResultCode.REGISTER_FAIL);
    }
    PasswordDO passwordDO = new PasswordDO();
    BeanUtils.copyProperties(passwordModel, passwordDO);
    passwordMapper.insertPassword(passwordDO);
    return passwordModel;
  }
}
