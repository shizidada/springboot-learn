package org.learn.web.controller;


import org.learn.rabbit.SimpleSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    @Autowired
    SimpleSender helloSender;

    @RequestMapping(value = "/api/v1/rabbit/hello", method = {RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    public void hello() {
        helloSender.sendToSimpleQueue();
    }
}
