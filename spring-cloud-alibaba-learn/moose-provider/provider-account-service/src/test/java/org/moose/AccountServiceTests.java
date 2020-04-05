package org.moose;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.moose.provider.account.service.AccountService;
import org.moose.provider.account.service.RoleService;
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

  @Resource
  private RoleService roleService;

  SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

  @Test
  public void createAccountTest() {

    Long accountId = idWorker.nextId();

    AccountDO accountDO = new AccountDO();
    accountDO.setAccountId(accountId);
    accountDO.setAccountName("张世豪");
    accountDO.setPhone("13612341234");
    accountDO.setStatus(1);
    accountDO.setIcon("https://icon.com");
    accountDO.setGender(1);
    accountDO.setBirthday(LocalDate.now());
    accountDO.setSourceType(1);
    accountDO.setCreateTime(LocalDateTime.now());
    accountDO.setUpdateTime(LocalDateTime.now());

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
    accountDTO.setPhone("13777777777");
    accountDTO.setStatus(1);
    accountDTO.setIcon("https://icon.com");
    accountDTO.setGender(1);
    accountDTO.setBirthday(LocalDate.now());
    accountDTO.setSourceType(2);
    accountDTO.setCreateTime(LocalDateTime.now());
    accountDTO.setUpdateTime(LocalDateTime.now());

    Long passwordId = idWorker.nextId();
    PasswordDTO passwordDTO = new PasswordDTO();
    passwordDTO.setAccountId(accountId);
    passwordDTO.setPasswordId(passwordId);
    passwordDTO.setCreateTime(LocalDateTime.now());
    passwordDTO.setUpdateTime(LocalDateTime.now());
    passwordDTO.setPassword(passwordEncoder.encode("123456"));

    boolean accountResult = accountService.add(accountDTO, passwordDTO);
    log.info("是否添加成功 [{}]", accountResult);
  }
}
