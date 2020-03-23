package org.moose.account.service;

import org.moose.account.model.dto.RoleDTO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/23 22:30
 * @see org.moose.account.service
 */

public interface RoleService {

  /**
   * 添加 角色
   *
   * @param roleDTO 角色
   * @return 是否添加成功
   */
  int add(RoleDTO roleDTO);
}
