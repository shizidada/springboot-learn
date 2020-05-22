package org.excel.operator.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.web.service.impl.AccountServiceImpl;
import org.excel.operator.web.service.model.RegisterInfoModel;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua
 */
@RestController
@RequestMapping(value = "/api/v1/account")
@Slf4j
public class AccountController {

  @Resource
  private AccountServiceImpl accountService;

  /**
   * spring security to login
   *
   * @PostMapping(value = "/login")
   *
   * @see SecurityConfiguration
   *
   */

  /**
   * spring security to register
   */
  @PostMapping(value = "/register")
  public ResponseResult register(@Valid RegisterInfoModel registerInfoModel, BindingResult result,
      HttpServletRequest request) {
    String url = request.getRequestURL().toString();
    String ip = request.getRemoteAddr();
    log.info("[accountName {}, ip {}, url {}]", ip, url);
    accountService.register(registerInfoModel);
    return ResponseResult.success(ResultCode.REGISTER_SUCCESS.getMessage());
  }
}
