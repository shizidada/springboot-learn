package org.learn.amqp.work;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.learn.amqp.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class Work1 {

    public static final String QUEUE_NAME = "test.work.queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        log.info("Worker1  Waiting for messages");

        /**
         * channel.basicQos(1);
         * 保证一次只分发一个 。
         * autoAck 是否自动回复，如果为 true 的话，每次生产者只要发送信息就会从内存中删除，
         * 那么如果消费者程序异常退出，那么就无法获取数据，我们当然是不希望出现这样的情况，
         * 所以才去手动回复，每当消费者收到并处理信息然后在通知生成者。最后从队列中删除这条信息。
         * 如果消费者异常退出，如果还有其他消费者，那么就会把队列中的消息发送给其他消费者，
         * 如果没有，等消费者启动时候再次发送
         */
        //每次从队列获取的数量
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                log.info("Worker1  Received Message {}", message);

                try {
//                    throw new Exception();
                    doWork();
                } catch (Exception e) {
                    channel.abort();
                } finally {
                    log.info("Worker1 Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };

        boolean autoAck = false;
        // 消息消费完成确认
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }

    private static void doWork() {
        try {
            Thread.sleep(1000); // 暂停 1 秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
