package org.moose.provider.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.moose.provider.account.model.domain.PasswordDO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-16 20:02:20:02
 * @see org.moose.provider.account.mapper
 */
@Mapper
public interface PasswordMapper {

  /**
   * 添加密码
   *
   * @param passwordDO PasswordDO
   * @return 是否添加成功
   */
  int insert(PasswordDO passwordDO);

  /**
   * 查询密码
   *
   * @param accountId 账号 id
   * @return 密码
   */
  PasswordDO findByAccountId(String accountId);
}
