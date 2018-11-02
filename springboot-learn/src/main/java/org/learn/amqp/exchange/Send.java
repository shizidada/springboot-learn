package org.learn.amqp.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.learn.amqp.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Send {

    // https://www.cnblogs.com/LipeiNet/category/896408.html

    public static final String EXCHANGE_NAME = "test.exchange.name";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");// fanout 表示分发，所有的消费者得到同样的队列信息

        //分发信息
        for (int i = 0; i < 5; i++) {
            String message = "This is test.exchange.name message  " + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            log.info("Send message  " + message);
        }

        channel.close();
        connection.close();
    }
}
