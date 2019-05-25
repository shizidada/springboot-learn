//package org.learn.backup.amqp.routing;
//
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
//    private static final String EXCHANGE_NAME = "test.direct.logs";
//    // 路由关键字
//    private static final String[] routingKeys = new String[]{"info", "warning", "error"};
//
//    public static void main(String[] args) throws IOException, TimeoutException {
//
//        Connection connection = ConnectionUtils.getConnection();
//
//        Channel channel = connection.createChannel();
//
//        //声明交换机
//        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//
//        //发送信息
//        for (String routingKey : routingKeys) {
//            String message = "RoutingSendDirect Send the message level: " + routingKey;
//            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
//            log.info("RoutingSendDirect Send" + routingKey + "':'" + message);
//        }
//        channel.close();
//        connection.close();
//    }
//}
