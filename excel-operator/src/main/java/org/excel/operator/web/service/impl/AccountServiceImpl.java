package org.excel.operator.web.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.component.SnowflakeIdWorker;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.mapper.AccountMapper;
import org.excel.operator.model.domain.AccountDO;
import org.excel.operator.model.dto.AccountDTO;
import org.excel.operator.model.dto.PasswordDTO;
import org.excel.operator.model.dto.RegisterInfoDTO;
import org.excel.operator.web.security.CustomUserDetails;
import org.excel.operator.web.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * @see org.excel.operator.web.service.impl
 */

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordServiceImpl passwordService;

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
  public boolean register(HttpServletRequest request, RegisterInfoDTO registerInfo) {
    String password = registerInfo.getPassword();
    String rePassword = registerInfo.getRePassword();
    String url = request.getRequestURL().toString();
    String ip = request.getRemoteAddr();
    log.info("register [ip {}], [url {}]", ip, url);

    // 比对两次密码是否一致
    if (!StringUtils.equals(password, rePassword)) {
      throw new BusinessException(ResultCode.SECOND_PASSWORD_ERROR);
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

      PasswordDTO passwordDTO = new PasswordDTO();
      passwordDTO.setAccountId(accountDO.getAccountId());
      passwordDTO.setPasswordId(snowflakeIdWorker.nextId());
      // 加密密码
      passwordDTO.setPassword(passwordEncoder.encode(registerInfo.getPassword()));

      accountMapper.insertAccount(accountDO);
      passwordService.addPassword(passwordDTO);
    } catch (Exception e) {
      log.info("register failed error [{}]", e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public boolean isLogin() {
    Authentication authentication = (Authentication) this.getAuthentication();
    if (authentication == null) {
      return false;
    }
    Object principal = authentication.getPrincipal();
    return principal instanceof CustomUserDetails;
  }

  @Override public Object getPrincipal() {
    Authentication authentication = (Authentication) this.getAuthentication();
    if (authentication != null) {
      return authentication.getPrincipal();
    }
    return null;
  }

  @Override public AccountDTO getAccountInfo() {
    CustomUserDetails userDetails = (CustomUserDetails) this.getPrincipal();
    return userDetails.getAccountDTO();
  }

  /**
   * 获取登录信息
   *
   * @return Authentication
   */
  private Object getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
