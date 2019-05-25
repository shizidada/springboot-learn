//package org.learn.backup.amqp.persistent;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.MessageProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.learn.backup.amqp.util.ConnectionUtils;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.concurrent.TimeoutException;
//
//@Slf4j
//public class Send {
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
//        for (int i = 0; i < 10; i++) {
//            String message = "this is persistent.queue message !!! " + new Date().getTime();
//            channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
//            log.info(message);
//        }
//
//        channel.close();
//        connection.close();
//    }
//}
