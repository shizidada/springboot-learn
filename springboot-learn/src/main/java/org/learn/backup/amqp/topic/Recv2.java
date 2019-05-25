//package org.learn.backup.amqp.topic;
//
//import com.rabbitmq.client.*;
//import lombok.extern.slf4j.Slf4j;
//import org.learn.backup.amqp.util.ConnectionUtils;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//@Slf4j
//public class Recv2 {
//    private static final String EXCHANGE_NAME = "test.topic.logs";
//
//    public static void main(String[] args) throws IOException, TimeoutException {
//        Connection connection = ConnectionUtils.getConnection();
//        Channel channel = connection.createChannel();
//
//        //声明一个匹配模式的交换机
//        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
//        String queueName = channel.queueDeclare().getQueue();
//        //路由关键字
//        String[] routingKeys = new String[]{"*.*.rabbit", "lazy.#"};
//        //绑定路由
//        for (String routingKey : routingKeys) {
//            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
//            log.info("Recv2 exchange:" + EXCHANGE_NAME + ", queue:" + queueName + ", BindRoutingKey:" + routingKey);
//        }
//        log.info("Recv2 Waiting for messages");
//
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String message = new String(body, "UTF-8");
//                log.info("Recv2 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
//            }
//        };
//        channel.basicConsume(queueName, true, consumer);
//    }
//}
