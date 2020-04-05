package org.moose.provider.account.service.impl;

import java.time.LocalDateTime;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcException;
import org.moose.commons.base.code.AccountCode;
import org.moose.commons.base.code.PhoneCode;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.moose.provider.account.constants.AccountDefaultConstants;
import org.moose.provider.account.mapper.AccountMapper;
import org.moose.provider.account.model.domain.AccountDO;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.model.dto.RoleDTO;
import org.moose.provider.account.service.AccountService;
import org.moose.provider.account.service.PasswordService;
import org.moose.provider.account.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 23:38
 * @see org.moose.provider.account.service.impl
 */
@Slf4j
@Service(version = "1.0.0")
@Transactional(rollbackFor = {Exception.class})
public class AccountServiceImpl implements AccountService {

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordService passwordService;

  @Resource
  private RoleService roleService;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Override
  public int add(AccountDTO accountDTO) {
    if (accountDTO == null) {
      throw new RpcException(AccountCode.ACCOUNT_MUST_NOT_BE_NULL.getCode(),
          AccountCode.ACCOUNT_MUST_NOT_BE_NULL.getMessage());
    }

    // 检查 accountName、phone
    // 用户名称是否存在
    String accountName = accountDTO.getAccountName();
    AccountDTO uniqueAccount = this.getAccountByAccountName(accountName);
    if (uniqueAccount != null) {
      throw new RpcException(AccountCode.ACCOUNT_NAME_IS_EXIST.getCode(),
          AccountCode.ACCOUNT_NAME_IS_EXIST.getMessage());
    }

    // 手机是否存在
    String phone = accountDTO.getPhone();
    uniqueAccount = this.getAccountByPhone(phone);
    if (uniqueAccount != null) {
      throw new RpcException(PhoneCode.PHONE_IS_EXIST.getCode(),
          PhoneCode.PHONE_IS_EXIST.getMessage());
    }

    AccountDO accountDO = new AccountDO();
    BeanUtils.copyProperties(accountDTO, accountDO);
    //TODO: set some default value for register account
    accountDO.setStatus(AccountDefaultConstants.DEFAULT_STATUS);
    accountDO.setIcon(AccountDefaultConstants.DEFAULT_ICON);
    accountDO.setCreateTime(LocalDateTime.now());
    accountDO.setUpdateTime(LocalDateTime.now());
    return accountMapper.insert(accountDO);
  }

  @Override
  public boolean add(AccountDTO accountDTO, PasswordDTO passwordDTO) {

    // add account
    Long accountId = snowflakeIdWorker.nextId();
    accountDTO.setAccountId(accountId);
    int account = this.add(accountDTO);

    // add password
    Long passwordId = snowflakeIdWorker.nextId();
    passwordDTO.setAccountId(accountId);
    passwordDTO.setPasswordId(passwordId);
    int password = passwordService.add(passwordDTO);

    // add role
    Long roleId = snowflakeIdWorker.nextId();
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setRoleId(roleId);
    // Default USER
    roleDTO.setRole("USER");
    roleDTO.setAccountId(accountId);
    int role = roleService.add(roleDTO);

    return account > 0 && password > 0 && role > 0;
  }

  @Override
  public AccountDTO getAccountByAccountName(String accountName) {
    AccountDO accountDO = accountMapper.findAccountByName(accountName);
    if (accountDO == null) {
      return null;
    }
    AccountDTO accountDTO = new AccountDTO();
    BeanUtils.copyProperties(accountDO, accountDTO);
    return accountDTO;
  }

  @Override public AccountDTO getAccountByPhone(String phone) {
    AccountDO accountDO = accountMapper.findAccountByPhone(phone);
    if (accountDO == null) {
      return null;
    }
    AccountDTO accountDTO = new AccountDTO();
    BeanUtils.copyProperties(accountDO, accountDTO);
    return accountDTO;
  }
}
