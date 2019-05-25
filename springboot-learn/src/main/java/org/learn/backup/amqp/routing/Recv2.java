//package org.learn.backup.amqp.routing;
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
//    private static final String EXCHANGE_NAME = "test.direct.logs";
//
//    // 路由关键字
//    private static final String[] routingKeys = new String[]{"warning", "info"};
//
//    public static void main(String[] args) throws IOException, TimeoutException {
//        Connection connection = ConnectionUtils.getConnection();
//
//        Channel channel = connection.createChannel();
//
//        //声明交换机
//        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//
//        //获取匿名队列名称
//        String queueName = channel.queueDeclare().getQueue();
//
//        //根据路由关键字进行绑定
//        for (String routingKey : routingKeys) {
//            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
//            log.info("ReceiveLogs2 exchange:" + EXCHANGE_NAME + "," + " queue:" + queueName + ", BindRoutingKey:" + routingKey);
//        }
//        log.info("ReceiveLogs2 Waiting for messages");
//
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String message = new String(body, "UTF-8");
//                log.info("ReceiveLogs2 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
//            }
//        };
//        channel.basicConsume(queueName, true, consumer);
//    }
//}
