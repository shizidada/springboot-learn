package org.moose.provider.account.service.impl;

import java.time.LocalDateTime;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.Service;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.provider.exception.ProviderRpcException;
import org.moose.provider.account.mapper.RoleMapper;
import org.moose.provider.account.model.domain.RoleDO;
import org.moose.provider.account.model.dto.RoleDTO;
import org.moose.provider.account.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public int add(RoleDTO roleDTO) {
    if (roleDTO == null) {
      throw new ProviderRpcException(ResultCode.ROLE_MUST_NOT_BE_NULL);
    }
    RoleDO roleDO = new RoleDO();
    BeanUtils.copyProperties(roleDTO, roleDO);
    roleDO.setCreateTime(LocalDateTime.now());
    roleDO.setUpdateTime(LocalDateTime.now());
    return roleMapper.insert(roleDO);
  }

  @Override public RoleDTO getRoleByAccountId(Long accountId) {
    if (accountId == null) {
      throw new ProviderRpcException(ResultCode.ACCOUNT_ID_MUST_NOT_BE_NULL);
    }

    RoleDO roleDO = roleMapper.findRoleByAccountId(accountId);

    RoleDTO roleDTO = new RoleDTO();
    BeanUtils.copyProperties(roleDO, roleDTO);
    return roleDTO;
  }
}
