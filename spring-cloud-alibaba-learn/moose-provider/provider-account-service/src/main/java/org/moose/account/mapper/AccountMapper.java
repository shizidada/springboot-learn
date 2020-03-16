package org.moose.account.mapper;

import org.moose.account.model.domain.AccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/11 22:56
 * @see org.moose.account.mapper
 */
@Mapper
public interface AccountMapper {

  /**
   * 添加
   *
   * @param accountDO AccountDO
   * @return 是否添加成功
   */
  int insert(AccountDO accountDO);

  /**
   * 根据账号 id 查询
   *
   * @param accountId 账号 id
   * @return AccountDO
   */
  AccountDO findById(String accountId);

  /**
   * 根据账号名称查询
   *
   * @param accountName 账号名称
   * @return AccountDO
   */
  AccountDO findByAccountName(String accountName);
}
