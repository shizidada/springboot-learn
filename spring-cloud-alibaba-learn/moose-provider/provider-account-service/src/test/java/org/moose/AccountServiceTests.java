package org.moose;

import java.util.Date;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.moose.provider.account.mapper.AccountMapper;
import org.moose.provider.account.mapper.PasswordMapper;
import org.moose.provider.account.model.domain.AccountDO;
import org.moose.provider.account.model.domain.PasswordDO;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.model.dto.RoleDTO;
import org.moose.provider.account.service.AccountService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

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

  SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

  @Test
  public void createAccountTest() {

    Long accountId = idWorker.nextId();

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

    Long passwordId = idWorker.nextId();

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

    Long accountId = idWorker.nextId();
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

    Long passwordId = idWorker.nextId();
    PasswordDTO passwordDTO = new PasswordDTO();
    passwordDTO.setAccountId(accountId);
    passwordDTO.setPasswordId(passwordId);
    passwordDTO.setCreateTime(new Date());
    passwordDTO.setUpdateTime(new Date());
    passwordDTO.setPassword(passwordEncoder.encode("123456"));

    Long roleId = idWorker.nextId();
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
