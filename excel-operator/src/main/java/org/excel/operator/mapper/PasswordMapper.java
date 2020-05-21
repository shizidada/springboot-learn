package org.excel.operator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.excel.operator.domain.PasswordDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/10/27 15:04
 * @see org.excel.operator.mapper
 */
@Mapper
public interface PasswordMapper {

  /**
   * 根据用户名 accountId 查找
   */
  PasswordDO findByAccountId(Long accountId);

  /**
   *  插入密码
   * @param passwordDO 密码持久化
   * @return passwordDO
   */
  void insertPassword(PasswordDO passwordDO);
}
