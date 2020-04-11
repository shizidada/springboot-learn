package org.moose.provider.payment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.moose.provider.payment.model.domain.AccountBalanceDO;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 11:46
 * @see org.moose.provider.payment.mapper
 */
@Mapper
public interface AccountBalanceMapper {
  /**
   * 添加账户余额
   *
   * @param accountBalanceDO 余额信息
   * @return 是否成功
   */
  int operatorAccountBalance(AccountBalanceDO accountBalanceDO);

  /**
   * 根据账户 id 查询
   * @param accountId
   * @return
   */
  AccountBalanceDO findByAccountId(Long accountId);

  /**
   * 更新账户余额
   * @param accountBalanceDO 账户余额信息
   * @return 是否成功
   */
  int updateBalanceByAccountId(AccountBalanceDO accountBalanceDO);
}
