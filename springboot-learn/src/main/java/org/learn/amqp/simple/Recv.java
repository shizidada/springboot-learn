package org.learn.amqp.simple;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.learn.amqp.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Recv {

    private static String QUEUE_NAME = "test.simple.queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("[*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info(new String(body, "utf-8"));
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
