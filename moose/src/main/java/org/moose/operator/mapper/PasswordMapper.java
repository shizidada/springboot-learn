package org.moose.operator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.moose.operator.model.domain.PasswordDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:04
 * @see org.moose.operator.mapper
 */
@Mapper
public interface PasswordMapper {

  /**
   * 根据用户名 accountId 查找
   *
   * @param accountId 账号 id
   * @return PasswordDO
   */
  PasswordDO findByAccountId(String accountId);

  /**
   * 插入密码
   *
   * @param passwordDO 密码持久化
   * @return passwordDO
   * @throws Exception
   */
  void insertPassword(PasswordDO passwordDO) throws Exception;
}
