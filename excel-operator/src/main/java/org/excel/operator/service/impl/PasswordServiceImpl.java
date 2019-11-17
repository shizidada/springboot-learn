package org.excel.operator.service.impl;

import javax.annotation.Resource;
import org.excel.operator.entity.AccountDO;
import org.excel.operator.entity.PasswordDO;
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

  @Resource PasswordMapper passwordMapper;

  @Override public PasswordModel findByAccountId(Long accountId) {
    PasswordDO passwordDO = passwordMapper.findByAccountId(accountId);
    PasswordModel passwordModel = this.convertModelFormDataObject(passwordDO);
    return passwordModel;
  }

  private PasswordModel convertModelFormDataObject(PasswordDO passwordDO) {
    PasswordModel passwordModel = new PasswordModel();
    BeanUtils.copyProperties(passwordDO, passwordModel);
    return passwordModel;
  }
}
