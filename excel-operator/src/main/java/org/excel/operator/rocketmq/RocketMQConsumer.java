package org.excel.operator.rocketmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * RocketMQ 消费者
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-21 22:25:22:25
 * @see org.excel.operator
 */
public class RocketMQConsumer {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) throws Exception {
    // 实例化消费者
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

    // 设置NameServer的地址
    consumer.setNamesrvAddr("localhost:9876");

    // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
    consumer.subscribe("test_demo_rocket_mq", "*");
    // 注册回调实现类来处理从broker拉取回来的消息
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
          ConsumeConcurrentlyContext context) {

        for (MessageExt msg : msgs) {
          System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(),
              new String(msg.getBody(), StandardCharsets.UTF_8));
        }
        // 标记该消息已经被成功消费
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });
    // 启动消费者实例
    consumer.start();
    System.out.printf("Consumer Started.%n");
  }
}
