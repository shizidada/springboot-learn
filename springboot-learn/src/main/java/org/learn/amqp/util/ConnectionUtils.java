package org.learn.amqp.util;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.learn.common.Constant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.RabbitMQ.HOST);
        factory.setPort(Constant.RabbitMQ.PORT);
        factory.setVirtualHost(Constant.RabbitMQ.VHOST);
        factory.setUsername(Constant.RabbitMQ.USERNAME);
        factory.setPassword(Constant.RabbitMQ.PASSWORD);
        return factory.newConnection();
    }
}
