package org.learn.backup;

public interface RabbitMQConstant {
    String HOST = "192.168.245.100";
    Integer PORT = 5672;
    String USERNAME = "admin";
    String PASSWORD = "123456";
    String VHOST = "/admin";

    interface RabbitQueue {
        String TEST_QUEUE_NAME = "test.queue";
    }
}
