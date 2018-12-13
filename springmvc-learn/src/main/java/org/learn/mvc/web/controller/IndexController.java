package org.learn.mvc.web.controller;

import org.learn.mvc.bean.UserBO;
import org.learn.mvc.web.service.UserService;
import org.learn.web.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends AbstractBaseController {

    @Autowired
    UserService userService;

    // http://localhost:8080/api/v1/user/query?action=query
    @RequestMapping(value = "api/v1/user/query", params = "action=query", method = {RequestMethod.POST, RequestMethod.GET}, produces = "text/plain;charset=UTF-8")
    public void query(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<UserBO> userBOS = userService.selectAllUser();
            map.put("users", userBOS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.print(response, map);
    }

    @RequestMapping(value = "api/v1/user/insert", params = "action=insert", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public void insert(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        UserBO userBO = new UserBO("admin", "admin");
        try {
            boolean user = userService.insertUser(userBO);
            if (user) {
                map.put("message", "添加成功。");
            } else {
                map.put("message", "添加失败。");

            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "添加失败。");
            map.put("error", e.getMessage());
        }
        this.print(response, map);
    }

}
