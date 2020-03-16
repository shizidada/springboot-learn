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
}
