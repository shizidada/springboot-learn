package org.learn.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SwaggerController extends AbstractBaseController {


    @RequestMapping(value = "/api/v1/swagger", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public void swagger(HttpServletResponse response){

        List<String> list = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            list.add("this is " + i );
        }

        this.print(response, list);

    }
}
