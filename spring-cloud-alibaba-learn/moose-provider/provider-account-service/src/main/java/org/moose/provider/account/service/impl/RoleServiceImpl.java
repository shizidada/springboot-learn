package org.moose.provider.account.service.impl;

import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.Service;
import org.moose.provider.account.mapper.RoleMapper;
import org.moose.provider.account.model.domain.RoleDO;
import org.moose.provider.account.model.dto.RoleDTO;
import org.moose.provider.account.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/23 22:52
 * @see org.moose.provider.account.service.impl
 */
@Component
@Service(version = "1.0.0")
public class RoleServiceImpl implements RoleService {

  @Resource
  private RoleMapper roleMapper;

  @Override
  public int add(RoleDTO roleDTO) {
    RoleDO roleDO = new RoleDO();
    BeanUtils.copyProperties(roleDTO, roleDO);
    return roleMapper.insert(roleDO);
  }

  @Override public RoleDTO get(RoleDTO roleDTO) {
    RoleDO roleDO = new RoleDO();
    BeanUtils.copyProperties(roleDTO, roleDO);
    roleDO = roleMapper.findRoleByAccountId(roleDO);

    BeanUtils.copyProperties(roleDO, roleDTO);
    return roleDTO;
  }
}
