package org.excel.operator.service;

import org.excel.operator.service.model.PasswordModel;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.excel.operator.service
 */
public interface PasswordService {

  /**
   * 根据账户id 查询密码
   *
   * @param accountId 账号ID
   * @return PasswordModel
   */
  PasswordModel findByAccountId(Long accountId);

  /**
   * 插入账号密码
   *
   * @param passwordModel 密码
   * @return 密码 model
   */
  PasswordModel insertPassword(PasswordModel passwordModel);
}
