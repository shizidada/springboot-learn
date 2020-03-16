package org.moose.account.service;

import org.moose.account.model.domain.AccountDO;
import org.moose.account.model.dto.AccountDTO;

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
   * @param accountDO 账号类
   * @return 是否添加成功
   */
  int add(AccountDO accountDO);

  /**
   * 通过用户名查询用户
   *
   * @param accountName 账号
   * @return 用户信息
   */
  AccountDTO get(String accountName);
}
