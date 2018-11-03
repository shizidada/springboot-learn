package org.learn.web.controller;

import org.learn.web.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController extends AbstractBaseController {

    // http://localhost:8080/api/v1/query?action=query
    @RequestMapping(value = "api/v1/query", params = "action=query", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    public void query(HttpServletResponse response) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("this is " + i);
        }
        this.print(response, list);
    }
}
