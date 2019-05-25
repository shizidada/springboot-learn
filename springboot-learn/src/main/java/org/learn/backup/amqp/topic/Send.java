//package org.learn.backup.amqp.topic;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import lombok.extern.slf4j.Slf4j;
//import org.learn.backup.amqp.util.ConnectionUtils;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//@Slf4j
//public class Send {
//    private static final String EXCHANGE_NAME = "test.topic.logs";
//
//    // * ：可以替代一个词
//    // #：可以替代0或者更多的词
//    public static void main(String[] args) throws IOException, TimeoutException {
//        Connection connection = null;
//        Channel channel = null;
//        try {
//            connection = ConnectionUtils.getConnection();
//            channel = connection.createChannel();
//
//            //声明一个匹配模式的交换机
//            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
//
//            //待发送的消息
//            String[] routingKeys = new String[]{
//                    "quick.orange.rabbit",
//                    "lazy.orange.elephant",
//                    "quick.orange.fox",
//                    "lazy.brown.fox",
//                    "quick.brown.fox",
//                    "quick.orange.male.rabbit",
//                    "lazy.orange.male.rabbit"
//            };
//            //发送消息
//            for (String severity : routingKeys) {
//                String message = "From " + severity + " routingKey' s message!";
//                channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
//                log.info("Send Sent '" + severity + "':'" + message + "'");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (connection != null) {
//                channel.close();
//                connection.close();
//            }
//        } finally {
//            if (connection != null) {
//                channel.close();
//                connection.close();
//            }
//        }
//    }
//}
