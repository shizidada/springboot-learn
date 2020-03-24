package org.moose.account.service;

import org.moose.account.model.domain.AccountDO;
import org.moose.account.model.domain.PasswordDO;
import org.moose.account.model.domain.RoleDO;
import org.moose.account.model.dto.AccountDTO;
import org.moose.account.model.dto.PasswordDTO;
import org.moose.account.model.dto.RoleDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 23:35
 * @see org.moose.account.service
 */
public interface AccountService {

  /**
   * 添加账号
   *
   * @param accountDTO 账号
   * @return 是否添加成功
   */
  int add(AccountDTO accountDTO);

  /**
   * 添加账号 和 密码
   *
   * @param accountDTO 账号
   * @param passwordDTO 密码
   * @param roleDTO 角色
   * @return 是否添加成功
   */
  boolean add(AccountDTO accountDTO, PasswordDTO passwordDTO, RoleDTO roleDTO);

  /**
   * 通过用户名查询用户
   *
   * @param accountName 账号
   * @return 用户信息
   */
  AccountDTO get(String accountName);
}
