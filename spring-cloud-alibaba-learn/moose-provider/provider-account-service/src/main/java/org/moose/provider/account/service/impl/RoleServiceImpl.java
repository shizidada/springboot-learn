package org.moose.provider.account.service.impl;

import java.time.LocalDateTime;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcException;
import org.moose.commons.base.code.AccountCode;
import org.moose.commons.base.code.RoleCode;
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
    if (roleDTO == null) {
      throw new RpcException(RoleCode.ROLE_MUST_NOT_BE_NULL.getCode(),
          RoleCode.ROLE_MUST_NOT_BE_NULL.getMessage());
    }
    RoleDO roleDO = new RoleDO();
    BeanUtils.copyProperties(roleDTO, roleDO);
    roleDO.setCreateTime(LocalDateTime.now());
    roleDO.setUpdateTime(LocalDateTime.now());
    return roleMapper.insert(roleDO);
  }

  @Override public RoleDTO getRoleByAccountId(Long accountId) {
    if (accountId == null) {
      throw new RpcException(AccountCode.ACCOUNT_ID_MUST_NOT_BE_NULL.getCode(),
          AccountCode.ACCOUNT_ID_MUST_NOT_BE_NULL.getMessage());
    }

    RoleDO roleDO = roleMapper.findRoleByAccountId(accountId);

    RoleDTO roleDTO = new RoleDTO();
    BeanUtils.copyProperties(roleDO, roleDTO);
    return roleDTO;
  }
}
