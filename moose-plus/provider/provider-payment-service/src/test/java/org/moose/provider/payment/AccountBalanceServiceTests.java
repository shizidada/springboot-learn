package org.moose.provider.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.moose.provider.payment.model.dto.AccountBalanceDTO;
import org.moose.provider.payment.service.AccountBalanceService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 11:58
 * @see org.moose.provider.payment
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
//@Rollback
@Slf4j
public class AccountBalanceServiceTests {

  @Resource
  private AccountBalanceService accountBalanceService;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testIncreaseAccountBalance() throws Exception {
    AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
    accountBalanceDTO.setId(snowflakeIdWorker.nextId());
    accountBalanceDTO.setAccountId(snowflakeIdWorker.nextId());
    accountBalanceDTO.setBalance(new BigDecimal("108940.00"));
    accountBalanceDTO.setCreateTime(LocalDateTime.now());
    accountBalanceDTO.setUpdateTime(LocalDateTime.now());
    log.info("AccountBalance {}", objectMapper.writeValueAsString(accountBalanceDTO));
    accountBalanceService.increaseAccountBalance(accountBalanceDTO);
  }

  @Test
  public void testReduceAccountBalance() {
    AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO();
    accountBalanceDTO.setAccountId(698553408331509761L);
    accountBalanceDTO.setBalance(new BigDecimal("108940.00"));
    accountBalanceService.reduceAccountBalance(accountBalanceDTO);
  }
}
