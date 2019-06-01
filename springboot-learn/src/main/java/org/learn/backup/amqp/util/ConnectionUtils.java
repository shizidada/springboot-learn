//package org.learn.backup.amqp.util;
//
//
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import org.learn.common.Constants;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//public class ConnectionUtils {
//
//    public static Connection getConnection() throws IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(Constants.RabbitMQ.HOST);
//        factory.setPort(Constants.RabbitMQ.PORT);
//        factory.setVirtualHost(Constants.RabbitMQ.VHOST);
//        factory.setUsername(Constants.RabbitMQ.USERNAME);
//        factory.setPassword(Constants.RabbitMQ.PASSWORD);
//        return factory.newConnection();
//    }
//}
