package org.learn.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.learn.common.Constant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = Constant.RabbitQueue.TEST_QUEUE_NAME)
public class SimpleReceiver1 {

    @RabbitHandler
    public void process(String message) {
        log.info(message);
    }
}
