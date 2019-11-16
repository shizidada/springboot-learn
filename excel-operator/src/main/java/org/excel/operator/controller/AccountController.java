package org.excel.operator.controller;

import javax.servlet.http.HttpServletRequest;
import org.excel.operator.common.api.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  @PostMapping(value = "/api/v1/account/login")
  public ResponseResult login(HttpServletRequest request) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    if (username == "admin" && password == "123456") {
      return ResponseResult.success("Login Success.");
    }
    return ResponseResult.fail("Username Or Password Error.");
  }
}
