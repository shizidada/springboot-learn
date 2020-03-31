package org.moose.business.oauth.service;

import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.RoleDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/30 20:42
 * @see org.moose.business.oauth.service
 */
public interface OAuth2Service {

  /**
   * 根据手机号查询账号
   *
   * @param phone 手机号
   * @return 账号
   */
  AccountDTO getAccountByPhone(String phone);

  /**
   * 查询账号角色
   *
   * @param accountId 账号 id
   * @return RoleDTO
   */
  RoleDTO getAccountRole(Long accountId);
}
