//package org.learn.backup.amqp.exchange;
//
//import com.rabbitmq.client.*;
//import lombok.extern.slf4j.Slf4j;
//import org.learn.backup.amqp.util.ConnectionUtils;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//@Slf4j
//public class Recv {
//
//    public static final String EXCHANGE_NAME = "test.exchange.name";
//
//    public static void main(String[] args) throws IOException, TimeoutException {
//
//        Connection connection = ConnectionUtils.getConnection();
//
//        Channel channel = connection.createChannel();
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); // fanout 表示分发，所有的消费者得到同样的队列信息
//
//        String queueName = channel.queueDeclare().getQueue();
//        channel.queueBind(queueName, EXCHANGE_NAME, ""); // 对队列进行绑定
//        log.info("Recv Waiting for messages");
//
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String message = new String(body, "UTF-8");
//                log.info("Received '" + message);
//            }
//        };
//        channel.basicConsume(queueName, true, consumer);// 队列会自动删除
//    }
//}
