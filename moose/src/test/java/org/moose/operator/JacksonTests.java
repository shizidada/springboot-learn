package org.moose.operator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.moose.operator.model.domain.AccountDO;
import org.moose.operator.model.dto.MessageInfoDTO;
import org.moose.operator.util.MapperUtils;
import org.moose.operator.util.SnowflakeIdWorker;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-23 12:13:12:13
 * @see org.moose.operator
 */
@Slf4j
public class JacksonTests {

  SnowflakeIdWorker snowflakeIdWorker;

  @Before
  public void runBeforeTestMethod() {
    snowflakeIdWorker = new SnowflakeIdWorker(1, 2);
    log.info("@Before - runBeforeTestMethod");
  }

  @Test
  public void testBase() {
    ObjectMapper mapper = new ObjectMapper();
    AccountDO accountDO = new AccountDO();
    accountDO.setAccountId(String.valueOf(snowflakeIdWorker.nextId()));
    try {
      String s = mapper.writeValueAsString(accountDO);
      log.info("s {}", s);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testMessageInfo() throws Exception {
    MessageInfoDTO messageInfo = new MessageInfoDTO();
    messageInfo.setMessage("hi");
    messageInfo.setToId("123");
    String messageInfoJson = MapperUtils.obj2json(messageInfo);
    // {"toId":"757308185127157760","message":"hi"}
    log.info(messageInfoJson);
  }
}
