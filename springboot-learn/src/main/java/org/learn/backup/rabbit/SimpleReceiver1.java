//package org.learn.backup.rabbit;
//
//import lombok.extern.slf4j.Slf4j;
//import org.learn.common.Constants;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RabbitListener(queues = Constants.RabbitQueue.TEST_QUEUE_NAME)
//public class SimpleReceiver1 {
//
//    @RabbitHandler
//    public void process(String message) {
//        log.info(message);
//    }
//}
