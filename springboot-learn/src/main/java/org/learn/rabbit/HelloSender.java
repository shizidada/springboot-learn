package org.learn.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.learn.common.Constant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendToSimpleQueue() {
        String message = "this is test simple queue message !!!  " + new Date();
        log.info(message);
        amqpTemplate.convertAndSend(Constant.RabbitQueue.TEST_QUEUE_NAME, message);
    }
}
