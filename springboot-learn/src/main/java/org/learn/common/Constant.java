package org.learn.common;

public interface Constant {

    interface RabbitMQ {
        String HOST = "192.168.245.100";
        Integer PORT = 5672;
        String USERNAME = "admin";
        String PASSWORD = "123456";
        String VHOST = "/admin";
    }

    interface RabbitQueue {
        String TEST_QUEUE_NAME = "test.queue";
    }

    String ERROR_MESSAGE = "访问失败";

    String SUCCESS_MESSAGE = "访问成功";

    Integer ERROR_CODE = -1;

    Integer SECCESS_CODE = 1;

    Integer FAILD_CODE = 0;

}
