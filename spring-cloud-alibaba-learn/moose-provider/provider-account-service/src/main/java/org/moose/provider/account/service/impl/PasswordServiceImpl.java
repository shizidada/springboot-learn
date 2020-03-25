package org.moose.provider.account.service.impl;

import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.Service;
import org.moose.provider.account.mapper.PasswordMapper;
import org.moose.provider.account.model.domain.PasswordDO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.service.PasswordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

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
  private PasswordMapper passwordMapper;

  @Override
  public int add(PasswordDTO passwordDTO) {
    PasswordDO passwordDO = new PasswordDO();
    BeanUtils.copyProperties(passwordDTO, passwordDO);
    return passwordMapper.insert(passwordDO);
  }

  @Override
  public PasswordDTO get(String accountId) {
    PasswordDO passwordDO = passwordMapper.findByAccountId(accountId);
    PasswordDTO passwordDTO = new PasswordDTO();
    BeanUtils.copyProperties(passwordDO, passwordDTO);
    return passwordDTO;
  }
}
