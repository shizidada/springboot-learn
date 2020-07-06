package org.moose.provider.account.service.impl;

import java.time.LocalDateTime;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.Service;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.provider.exception.ProviderRpcException;
import org.moose.provider.account.mapper.PasswordMapper;
import org.moose.provider.account.model.domain.PasswordDO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.service.PasswordService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/16 23:40
 * @see org.moose.provider.account.service.impl
 */
@Component
@Service(version = "1.0.0")
public class PasswordServiceImpl implements PasswordService {

  @Resource
  private PasswordEncoder passwordEncoder;

  @Resource
  private PasswordMapper passwordMapper;

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public int add(PasswordDTO passwordDTO) {
    if (passwordDTO == null) {
      throw new ProviderRpcException(ResultCode.PASSWORD_MUST_NOT_BE_NULL);
    }
    PasswordDO passwordDO = new PasswordDO();
    BeanUtils.copyProperties(passwordDTO, passwordDO);
    passwordDO.setPassword(passwordEncoder.encode(passwordDO.getPassword()));
    passwordDO.setCreateTime(LocalDateTime.now());
    passwordDO.setUpdateTime(LocalDateTime.now());
    return passwordMapper.insert(passwordDO);
  }

  @Override
  public PasswordDTO get(Long accountId) {
    if (accountId == null) {
      return null;
    }
    PasswordDO passwordDO = passwordMapper.findByAccountId(accountId);
    PasswordDTO passwordDTO = new PasswordDTO();
    BeanUtils.copyProperties(passwordDO, passwordDTO);
    return passwordDTO;
  }
}
