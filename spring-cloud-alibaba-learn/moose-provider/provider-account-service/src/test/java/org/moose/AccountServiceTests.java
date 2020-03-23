package org.moose;

import java.util.Date;

import java.util.UUID;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.account.mapper.AccountMapper;
import org.moose.account.mapper.PasswordMapper;
import org.moose.account.model.domain.AccountDO;
import org.moose.account.model.domain.PasswordDO;
import org.moose.account.model.domain.RoleDO;
import org.moose.account.model.dto.AccountDTO;
import org.moose.account.model.dto.PasswordDTO;
import org.moose.account.model.dto.RoleDTO;
import org.moose.account.service.AccountService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号服务测试类
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/15 17:42
 * @see org.moose
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
//@Rollback
@Slf4j
public class AccountServiceTests {

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordMapper passwordMapper;

  @Resource
  private PasswordEncoder passwordEncoder;

  @Resource
  private AccountService accountService;

  @Test
  public void createAccountTest() {

    String accountId = UUID.randomUUID().toString().replace("-", "");

    AccountDO accountDO = new AccountDO();
    accountDO.setAccountId(accountId);
    accountDO.setAccountName("张世豪");
    accountDO.setNickName("阿豪");
    accountDO.setPhone("13612341234");
    accountDO.setStatus(1);
    accountDO.setIcon("https://icon.com");
    accountDO.setGender(1);
    accountDO.setBirthday(new Date());
    accountDO.setSourceType(1);
    accountDO.setCreateTime(new Date());
    accountDO.setUpdateTime(new Date());

    String passwordId = UUID.randomUUID().toString().replace("-", "");

    PasswordDO passwordDO = new PasswordDO();
    passwordDO.setAccountId(accountId);
    passwordDO.setPasswordId(passwordId);
    passwordDO.setPassword(passwordEncoder.encode("123456"));

    accountMapper.insert(accountDO);

    //int a = 10 / 0;

    passwordMapper.insert(passwordDO);

    log.info("[{}]", accountDO);
  }

  @Test
  public void addAccountAndPassword() {

    String accountId = UUID.randomUUID().toString().replace("-", "");
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setAccountId(accountId);
    accountDTO.setAccountName("tom");
    accountDTO.setNickName("净空法师");
    accountDTO.setPhone("13777777777");
    accountDTO.setStatus(1);
    accountDTO.setIcon("https://icon.com");
    accountDTO.setGender(1);
    accountDTO.setBirthday(new Date());
    accountDTO.setSourceType(2);
    accountDTO.setCreateTime(new Date());
    accountDTO.setUpdateTime(new Date());

    String passwordId = UUID.randomUUID().toString().replace("-", "");
    PasswordDTO passwordDTO = new PasswordDTO();
    passwordDTO.setAccountId(accountId);
    passwordDTO.setPasswordId(passwordId);
    passwordDTO.setCreateTime(new Date());
    passwordDTO.setUpdateTime(new Date());
    passwordDTO.setPassword(passwordEncoder.encode("123456"));

    String roleId = UUID.randomUUID().toString().replace("-", "");
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setRoleId(roleId);
    roleDTO.setRole("ADMIN");
    roleDTO.setAccountId(accountId);
    roleDTO.setCreateTime(new Date());
    roleDTO.setUpdateTime(new Date());

    boolean result = accountService.add(accountDTO, passwordDTO, roleDTO);
    log.info("是否添加成功 [{}]", result);
  }
}
