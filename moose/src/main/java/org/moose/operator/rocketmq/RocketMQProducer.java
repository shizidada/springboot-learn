package org.moose.operator.rocketmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.moose.operator.model.dto.PaymentInfoDTO;

/**
 * RocketMQ 生产者
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-21 22:35:22:35
 * @see org.moose.operator
 */
public class RocketMQProducer {

  private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void main(String[] args) throws Exception {
    // 实例化消息生产者Producer
    DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
    // 设置NameServer的地址
    producer.setNamesrvAddr("rocketmq.moose.com:9876");
    // 启动Producer实例
    producer.start();
    for (int i = 0; i < 100; i++) {
      // 创建消息，并指定Topic，Tag和消息体
      PaymentInfoDTO paymentInfo = new PaymentInfoDTO();
      paymentInfo.setPaymentId(String.format("%s", i));
      paymentInfo.setFromName("zhangsan");
      paymentInfo.setReceiveName("lisi");
      Message msg = new Message(
          /* Topic */
          "test_demo_rocket_mq",
          /* Tag */
          "TagA",
          /* Message body */
          (OBJECT_MAPPER.writeValueAsString(paymentInfo)).getBytes(RemotingHelper.DEFAULT_CHARSET)
      );
      // 发送消息到一个Broker
      SendResult sendResult = producer.send(msg);
      // 通过sendResult返回消息是否成功送达
      System.out.printf("%s%n", sendResult);
    }
    // 如果不再发送消息，关闭Producer实例。
    producer.shutdown();
  }
}
