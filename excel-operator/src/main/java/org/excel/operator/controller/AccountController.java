package org.excel.operator.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.excel.operator.common.api.ResponseCode;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.service.impl.AccountServiceImpl;
import org.excel.operator.service.model.RegisterInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua
 */
@CrossOrigin(value = "*")
@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountController {

  private final Logger logger = LoggerFactory.getLogger(AccountController.class);

  @Resource AccountServiceImpl accountService;

  @PostMapping(value = "/login")
  public ResponseResult login(HttpServletRequest request) {
    String url = request.getRequestURL().toString();
    String ip = request.getRemoteAddr();
    String accountName = request.getParameter("accountName");
    String password = request.getParameter("password");
    logger.info("[accountName {}, ip {}, url {}]", accountName, ip, url);
    accountService.login(accountName, password);
    request.getSession().setAttribute("accountName", accountName);
    return ResponseResult.success(ResponseCode.LOGIN_SUCCESS.getMessage());
  }

  @PostMapping(value = "/register")
  public ResponseResult register(@Valid RegisterInfoModel registerInfoModel, BindingResult result,
      HttpServletRequest request) {
    String url = request.getRequestURL().toString();
    String ip = request.getRemoteAddr();
    logger.info("[accountName {}, ip {}, url {}]", ip, url);
    accountService.register(registerInfoModel);
    return ResponseResult.success(ResponseCode.REGISTER_SUCCESS.getMessage());
  }
}
