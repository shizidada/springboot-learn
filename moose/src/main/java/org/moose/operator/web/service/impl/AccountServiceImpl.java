package org.moose.operator.web.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.util.SnowflakeIdWorker;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.mapper.AccountMapper;
import org.moose.operator.model.domain.AccountDO;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.dto.PasswordDTO;
import org.moose.operator.model.dto.RegisterInfoDTO;
import org.moose.operator.web.security.component.CustomUserDetails;
import org.moose.operator.web.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.moose.operator.web.service.impl
 */

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordServiceImpl passwordService;

  @Resource
  private LoginServiceImpl loginService;

  @Resource
  private PasswordEncoder passwordEncoder;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Override public AccountDTO getByAccountName(String accountName) {
    accountName = accountName.trim();
    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    if (accountDO == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    AccountDTO accountDTO = new AccountDTO();
    BeanUtils.copyProperties(accountDO, accountDTO);
    return accountDTO;
  }

  @Override public AccountDTO getAccountByPhone(String phone) {
    AccountDO accountDO = accountMapper.findByPhone(phone);
    if (accountDO == null) {
      throw new BusinessException(ResultCode.PHONE_NOT_EXITS);
    }
    AccountDTO accountDTO = new AccountDTO();
    BeanUtils.copyProperties(accountDO, accountDTO);
    return accountDTO;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public ResponseResult<Object> saveAccount(HttpServletRequest request,
      RegisterInfoDTO registerInfo) {
    String password = registerInfo.getPassword();
    String rePassword = registerInfo.getRePassword();
    String url = request.getRequestURL().toString();
    String ip = request.getRemoteAddr();
    log.info("register [ip {}], [url {}]", ip, url);

    // 比对两次密码是否一致
    if (!StringUtils.equals(password, rePassword)) {
      throw new BusinessException(ResultCode.PASSWORD_ERROR);
    }

    // 查询对应 账号名称
    String accountName = registerInfo.getAccountName();
    AccountDO account = accountMapper.findByAccountName(accountName);
    if (account != null) {
      throw new BusinessException(ResultCode.ACCOUNT_NAME_EXITS);
    }

    // 查询手机号是否存在
    String phone = registerInfo.getPhone();
    account = accountMapper.findByPhone(phone);
    if (account != null) {
      throw new BusinessException(ResultCode.PHONE_EXITS);
    }

    try {
      AccountDO accountDO = new AccountDO();
      accountDO.setAccountId(snowflakeIdWorker.nextId());
      accountDO.setAccountName(registerInfo.getAccountName());
      accountDO.setPhone(registerInfo.getPhone());
      accountDO.setAvatar(registerInfo.getAvatar());
      accountDO.setGender(registerInfo.getGender());

      PasswordDTO passwordDTO = new PasswordDTO();
      passwordDTO.setAccountId(accountDO.getAccountId());
      passwordDTO.setPasswordId(snowflakeIdWorker.nextId());
      // 加密密码
      passwordDTO.setPassword(passwordEncoder.encode(registerInfo.getPassword()));

      accountMapper.insertAccount(accountDO);
      passwordService.savePassword(passwordDTO);
    } catch (Exception e) {
      log.info("register failed error [{}]", e.getMessage());
      throw new BusinessException(ResultCode.REGISTER_FAIL);
    }
    return new ResponseResult<>(Boolean.TRUE, "注册成功");
  }

  @Override public ResponseResult<Object> getAccountInfo() {
    Object principal = loginService.getPrincipal();
    if (principal instanceof CustomUserDetails) {
      CustomUserDetails userDetails = (CustomUserDetails) principal;
      return new ResponseResult<>(userDetails.getAccountDTO(), "获取用户信息成功");
    }
    return new ResponseResult<>(ResultCode.FAIL.getCode(), "获取用户信息失败");
  }
}
