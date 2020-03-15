package org.moose;

import java.util.Date;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.account.mapper.AccountMapper;
import org.moose.account.model.domain.AccountDO;
import org.springframework.boot.test.context.SpringBootTest;
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

  @Test
  public void createAccountDTOTest() {
    AccountDO accountDO = new AccountDO();
    accountDO.setAccountId(574839201487904L);
    accountDO.setAccountName("张世豪");
    accountDO.setNickName("阿豪");
    accountDO.setPhone("13586968698");
    accountDO.setStatus(1);
    accountDO.setIcon("");
    accountDO.setGender(1);
    accountDO.setBirthday(new Date());
    accountDO.setSourceType(1);
    accountDO.setCreateTime(new Date());
    accountDO.setUpdateTime(new Date());

    accountMapper.insert(accountDO);
    log.info("[{}]", accountDO);
  }
}
