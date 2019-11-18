package org.excel.operator.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.service.impl.AccountServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua
 */
@RestController
public class AccountController {

  @Resource AccountServiceImpl accountService;

  @PostMapping(value = "/api/v1/account/login")
  public ResponseResult login(HttpServletRequest request) {
    String accountName = request.getParameter("accountName");
    if (accountName == null) {
      return ResponseResult.success("AccountName can't be null.");
    }
    String password = request.getParameter("password");
    if (password == null) {
      return ResponseResult.success("Password can't be null.");
    }
    boolean isLogin = accountService.login(accountName, password);
    if (isLogin) {
      request.getSession().setAttribute("accountName", accountName);
      return ResponseResult.success("Login Success.");
    }
    return ResponseResult.fail("AccountName Or Password Error.");
  }
}
