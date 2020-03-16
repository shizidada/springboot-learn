package org.moose.account.service;

import org.moose.account.model.dto.PasswordDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/15 18:09
 * @see org.moose.account.service
 */
public interface PasswordService {

  /**
   * 通过账号 id 查询密码
   *
   * @param accountId 账号
   * @return 用户信息
   */
  PasswordDTO get(String accountId);
}
