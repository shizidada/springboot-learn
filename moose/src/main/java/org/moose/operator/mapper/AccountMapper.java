package org.moose.operator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moose.operator.model.domain.AccountDO;

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
public interface AccountMapper {

  /**
   * 根据用户名查找
   *
   * @param accountName
   * @return AccountDO
   */
  AccountDO findByAccountName(String accountName);

  /**
   * 根据手机号码查找
   *
   * @param phone
   * @return AccountDO
   */
  AccountDO findByPhone(String phone);

  /**
   * 插入 AccountDO 数据
   *
   * @param accountDO 账号信息
   * @return AccountDO
   * @throws Exception
   */
  void insertAccount(AccountDO accountDO) throws Exception;

  /**
   * 根据 accountId 更新 AccountDO 数据
   *
   * @param accountName 账号名称
   * @param accountId 账号Id
   * @return 是否更新成功
   * @throws Exception
   */
  void updateAccountNameByAccountId(@Param("accountName") String accountName,
      @Param("accountId") String accountId);
}
