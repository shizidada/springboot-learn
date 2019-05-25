//package org.learn.backup.amqp.work;
//
//import com.rabbitmq.client.*;
//import lombok.extern.slf4j.Slf4j;
//import org.learn.backup.amqp.util.ConnectionUtils;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//@Slf4j
//public class Work2 {
//
//    public static final String QUEUE_NAME = "test.work.queue";
//
//    public static void main(String[] args) throws IOException, TimeoutException {
//
//        Connection connection = ConnectionUtils.getConnection();
//
//        final Channel channel = connection.createChannel();
//
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//
//        log.info("Worker1  Waiting for messages");
//
//        //每次从队列获取的数量
//        channel.basicQos(1);
//
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                String message = new String(body, "utf-8");
//                log.info("Worker1  Received Message {}", message);
//
//                try {
//                    doWork();
//                } catch (Exception e) {
//                    channel.abort();
//                } finally {
//                    log.info("Worker2 Done");
//                    channel.basicAck(envelope.getDeliveryTag(), false);
//                }
//
//            }
//        };
//
//        boolean autoAck = false;
//        // 消息消费完成确认
//        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
//    }
//
//    private static void doWork() {
//        try {
//            Thread.sleep(2000); // 暂停 2 秒钟
//        } catch (InterruptedException _ignored) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
