package org.moose.operator.web.service;

import org.moose.operator.model.dto.PasswordDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.moose.operator.web.service
 */
public interface PasswordService {

  /**
   * 根据账户id 查询密码
   *
   * @param accountId 账号ID
   * @return PasswordModel
   */
  PasswordDTO getByAccountId(Long accountId);

  /**
   * 添加账号对应密码
   *
   * @param passwordDTO 密码
   * @return 密码 model
   * @throws Exception
   */
  PasswordDTO savePassword(PasswordDTO passwordDTO) throws Exception;
}
