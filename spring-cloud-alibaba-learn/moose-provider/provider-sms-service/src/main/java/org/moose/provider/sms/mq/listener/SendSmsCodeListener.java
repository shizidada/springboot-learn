package org.moose.provider.sms.mq.listener;

import java.nio.charset.StandardCharsets;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.moose.provider.sms.service.SmsSendService;
import org.springframework.stereotype.Component;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 14:23:14:23
 * @see org.moose.provider.sms.mq
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.sms.topic}", consumerGroup = "${mq.sms.consumer.group}", messageModel = MessageModel.BROADCASTING)
public class SendSmsCodeListener implements RocketMQListener<MessageExt> {

  @Resource
  private SmsSendService smsSendService;

  @Override
  public void onMessage(MessageExt messageExt) {
    String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
    smsSendService.add(body);
    log.info("短信服务接受到消息 :: {} ", body);
  }
}
