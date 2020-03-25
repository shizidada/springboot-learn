package org.moose.provider.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.moose.provider.account.model.domain.RoleDO;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/23 22:32
 * @see org.moose.provider.account.mapper
 */

@Mapper
public interface RoleMapper {

  /**
   * 添加 角色
   *
   * @param roleDO 角色
   * @return 是否添加成功
   */
  int insert(RoleDO roleDO);
}
