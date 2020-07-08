package org.moose.business.user.web.service;

import org.moose.provider.account.model.dto.AccountDTO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-24 13:31:13:31
 * @see org.moose.business.user.web.service
 */
public interface UserBaseService {

  /**
   * 根据手机号码查询账号
   *
   * @param phone 手机号码
   * @return 查询结果
   */
  AccountDTO findByPhone(String phone);
}
