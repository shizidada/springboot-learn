package org.learn.controller;

import org.learn.common.api.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/api/v1/test")
    @ResponseBody
    public AjaxResult test() {
        Map<String, Object> data = new HashMap<>();
        data.put("test", "this is test data");
        return AjaxResult.success("访问测试接口", data);
    }
}
