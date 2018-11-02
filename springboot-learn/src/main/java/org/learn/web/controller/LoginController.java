package org.learn.web.controller;


import org.learn.bean.Results;
import org.learn.common.Constant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController extends AbstractBaseController {

//    @ResponseBody
    @RequestMapping(value = "api/v1/login", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public void login(HttpServletResponse response, HttpServletRequest request) {
        Results results = new Results();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, Object> map = new HashMap<String, Object>();
        if (username.equals("learn") && password.equals("1234")) {
            map.put("username", username);
            map.put("password", password);

            results.setData(map);
            results.setCode(Constant.SECCESS_CODE);
            results.setStatus(Boolean.TRUE);
            results.setMessage(Constant.SUCCESS_MESSAGE);
        } else {
            results.setCode(Constant.FAILD_CODE);
            results.setStatus(Boolean.FALSE);
            results.setMessage("账号或密码错误！");
        }
        this.print(response, results);
    }
}
