//package org.learn.backup.amqp.persistent;
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
//    public static final String EXCHANGE_NAME = "persisitent.exchange.name";
//    public static final String QUEUE_NAME = "persisitent.queue";
//
//    public static void main(String[] args) throws IOException, TimeoutException {
//        Connection connection = ConnectionUtils.getConnection();
//        Channel channel = connection.createChannel();
//
//        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true, false, null);
//
//        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
//        log.info("Recv Waiting for messages");
//
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                log.info(new String(body, "utf-8"));
//            }
//        };
//
//        boolean autoAck = false;
//        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
//
//    }
//}
