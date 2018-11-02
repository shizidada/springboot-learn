package org.learn.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class IndexController {

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        System.out.println(request);
        return "index";
    }
}
