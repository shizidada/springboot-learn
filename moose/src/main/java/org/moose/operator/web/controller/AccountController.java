package org.moose.operator.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.model.params.AuthTokenParam;
import org.moose.operator.model.params.LoginParam;
import org.moose.operator.model.params.RegisterInfoParam;
import org.moose.operator.web.service.AccountService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountController {

  @Resource
  private AccountService accountService;

  /**
   * spring security oauth2.0 to register
   */
  @PostMapping(value = "/register")
  public ResponseResult<Object> register(@Valid RegisterInfoParam registerInfoParam,
      BindingResult result,
      HttpServletRequest request) {
    return new ResponseResult<>(accountService.saveAccount(request, registerInfoParam), "注册成功");
  }

  /**
   * spring security oauth2.0 to login
   */
  @PostMapping(value = "/login")
  public ResponseResult<Object> login(@Valid LoginParam loginParam,
      BindingResult result) {
    return new ResponseResult<>(accountService.getToken(loginParam), "获取 access token 成功");
  }

  /**
   * spring security oauth2.0 to logout
   */
  @PostMapping(value = "/logout")
  public ResponseResult<Object> logout(AuthTokenParam tokenParam) {
    String accessToken = tokenParam.getAccessToken();
    return new ResponseResult<>(accountService.removeToken(accessToken), "退出成功");
  }

  @PostMapping(value = "/status")
  public ResponseResult<Object> status() {
    return new ResponseResult<>(accountService.isLogin(), "获取登录状态成功");
  }
}
