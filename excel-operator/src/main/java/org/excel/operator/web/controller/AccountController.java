package org.excel.operator.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.model.dto.AccountDTO;
import org.excel.operator.model.dto.RegisterInfoDTO;
import org.excel.operator.web.service.impl.AccountServiceImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseResult<Boolean> register(@Valid @RequestBody RegisterInfoDTO registerInfoDTO,
      BindingResult result,
      HttpServletRequest request) {
    return new ResponseResult<>(accountService.register(request, registerInfoDTO), "登录成功");
  }

  @PostMapping(value = "/info")
  public ResponseResult<AccountDTO> info() {
    return new ResponseResult<>(accountService.getAccountInfo(), "获取用户信息成功");
  }
}
