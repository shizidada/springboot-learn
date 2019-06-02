package org.learn.controller;

import org.learn.common.api.AjaxResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    //    @PreAuthorize("hasRole('ROLE_MEMBER')")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @RequestMapping(value = "/api/v1/test", method = {RequestMethod.POST})
    @ResponseBody
    public AjaxResult testMember() {
        Map<String, Object> data = new HashMap<>();
        data.put("test", "this is test member data");
        return AjaxResult.success("访问测试接口", data);
    }

    @RequestMapping(value = "/api/v1/index/test", method = {RequestMethod.POST})
    @ResponseBody
    public AjaxResult test1() {
        Map<String, Object> data = new HashMap<>();
        data.put("test", "this is /api/v1/index/test not PreAuthorize data");
        return AjaxResult.success("访问测试接口", data);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/api/v1/test_admin", method = {RequestMethod.POST})
    @ResponseBody
    public AjaxResult testAdmin() {
        Map<String, Object> data = new HashMap<>();
        data.put("test", "this is test admin data");
        return AjaxResult.success("访问测试接口", data);
    }
}
