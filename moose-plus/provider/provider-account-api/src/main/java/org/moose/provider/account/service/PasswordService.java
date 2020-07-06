package org.moose.provider.account.service;

import org.moose.provider.account.model.dto.PasswordDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/15 18:09
 * @see org.moose.provider.account.service
 */
public interface PasswordService {


  /**
   * 添加密码
   * @param passwordDTO 密码
   * @return 是否添加成功
   */
  int add(PasswordDTO passwordDTO);

  /**
   * 通过账号 id 查询密码
   *
   * @param accountId 账号
   * @return 用户信息
   */
  PasswordDTO get(Long accountId);
}
