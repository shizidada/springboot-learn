package org.learn.amqp.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.learn.amqp.util.ConnectionUtils;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Send {

    public static final String QUEUE_NAME = "test.work.queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 10; i++) {
            String message = "test work queue message !!!" + new Date();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            log.info(message);
        }

        channel.close();
        connection.close();
    }
}
