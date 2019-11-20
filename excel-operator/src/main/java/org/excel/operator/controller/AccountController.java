package org.excel.operator.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.excel.operator.common.api.ResponseCode;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.service.impl.AccountServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
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
    String password = request.getParameter("password");
    accountService.login(accountName, password);
    request.getSession().setAttribute("accountName", accountName);
    return ResponseResult.success(ResponseCode.LOGIN_SUCCESS.getMessage());
  }
}
