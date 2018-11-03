package org.learn.web.controller;


import org.learn.rabbit.SimpleSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class RabbitMQController {

    @Autowired
    SimpleSender helloSender;

    @RequestMapping("/hello")
    public void hello() {
        helloSender.sendToSimpleQueue();
    }
}
