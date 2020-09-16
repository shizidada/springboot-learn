package org.moose.operator;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.operator.mapper.SmsCodeMapper;
import org.moose.operator.model.domain.SmsCodeDO;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsCodeSenderTests {

  @Resource
  private SmsCodeMapper smsCodeMapper;

  @Test
  public void testInsertSmsCode() {
    String smsCode = RandomStringUtils.randomNumeric(6);
    SmsCodeDO smsCodeDO = new SmsCodeDO();
    smsCodeDO.setPhone("1569878989");
    smsCodeDO.setType("login_type");
    smsCodeDO.setCode(smsCode);
    smsCodeMapper.insertSmsCode(smsCodeDO);
    log.info("generator sms code : [{}]", smsCode);
  }
}
