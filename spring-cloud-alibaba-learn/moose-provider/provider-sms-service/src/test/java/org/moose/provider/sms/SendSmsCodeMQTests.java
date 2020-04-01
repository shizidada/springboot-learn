package org.moose.provider.sms;

import com.alibaba.fastjson.JSON;
import java.time.LocalDateTime;

import javax.annotation.Resource;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moose.provider.sms.model.dto.SmsCodeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 15:26:15:26
 * @see org.moose.provider.sms
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderSmsServiceApplication.class)
public class SendSmsCodeMQTests {

  @Resource
  private RocketMQTemplate rocketMQTemplate;

  @Value("${mq.sms.topic}")
  private String topic;

  @Value("${mq.sms.tag}")
  private String tag;

  @Test
  public void testSendSmsCode() {
    SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
    smsCodeDTO.setPhone("13598989888");
    smsCodeDTO.setVerifyCode("924233");
    smsCodeDTO.setType("0");
    smsCodeDTO.setExpiredTime(LocalDateTime.now());
    smsCodeDTO.setCreateTime(LocalDateTime.now());
    smsCodeDTO.setUpdateTime(LocalDateTime.now());
    try {
      Message msg = new Message(topic, tag, "sms-code", JSON.toJSONString(smsCodeDTO).getBytes());
      rocketMQTemplate.getProducer().send(msg);
      System.in.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
