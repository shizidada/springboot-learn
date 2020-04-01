package org.moose.provider.account.service;

import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.model.dto.RoleDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 23:35
 * @see org.moose.provider.account.service
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

  /**
   * 根据 phone 查询
   *
   * @param phone 根据 phone 查找
   * @return 是否存在
   */
  AccountDTO getAccountByPhone(String phone);
}
