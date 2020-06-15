package org.excel.operator.web.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.PasswordMapper;
import org.excel.operator.model.domain.PasswordDO;
import org.excel.operator.model.dto.PasswordDTO;
import org.excel.operator.web.service.PasswordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 20:55
 * @see org.excel.operator.web.service.impl
 */

@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService {

  @Resource
  private PasswordMapper passwordMapper;

  @Override public PasswordDTO getByAccountId(Long accountId) {
    PasswordDO passwordDO = passwordMapper.findByAccountId(accountId);
    if (passwordDO == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }
    PasswordDTO passwordDTO = new PasswordDTO();
    BeanUtils.copyProperties(passwordDO, passwordDTO);
    return passwordDTO;
  }

  /**
   * 插入密码
   *
   * @param passwordDTO 密码
   * @return
   */
  @Override
  public PasswordDTO addPassword(PasswordDTO passwordDTO) throws Exception {
    if (passwordDTO == null) {
      throw new BusinessException(ResultCode.REGISTER_FAIL);
    }
    PasswordDO passwordDO = new PasswordDO();
    BeanUtils.copyProperties(passwordDTO, passwordDO);
    passwordMapper.insertPassword(passwordDO);
    return passwordDTO;
  }
}
