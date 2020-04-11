package org.moose.provider.payment.service;

import org.moose.provider.payment.model.dto.AccountBalanceDTO;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 11:59
 * @see org.moose.provider.payment.service
 */
public interface AccountBalanceService {

  /**
   * 查询对应账户余额
   *
   * @param accountId 账户 id
   * @return AccountBalanceDTO
   */
  public AccountBalanceDTO getBalanceByAccountId(Long accountId);

  /**
   * 添加账户余额
   * @param accountBalanceDTO
   * @return 是否操作成功
   */
  public void increaseAccountBalance(AccountBalanceDTO accountBalanceDTO);

  /**
   * 添加账户余额
   * @param accountBalanceDTO
   * @return 是否操作成功
   */
  public void reduceAccountBalance(AccountBalanceDTO accountBalanceDTO);
}
