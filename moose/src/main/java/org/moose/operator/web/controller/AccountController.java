package org.moose.operator.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.R;
import org.moose.operator.model.params.AuthTokenParam;
import org.moose.operator.model.params.LoginInfoParam;
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
  public R<Object> register(@Valid RegisterInfoParam registerInfoParam, BindingResult result,
      HttpServletRequest request) {
    return R.ok(accountService.saveAccount(request, registerInfoParam), "用户注册");
  }

  /**
   * spring security oauth2.0 to login
   */
  @PostMapping(value = "/login")
  public R<Object> login(@Valid LoginInfoParam loginInfoParam, BindingResult result) {
    return R.ok(accountService.getToken(loginInfoParam), "获取 access token");
  }

  /**
   * spring security oauth2.0 to logout
   */
  @PostMapping(value = "/logout")
  public R<Object> logout(AuthTokenParam tokenParam) {
    String accessToken = tokenParam.getAccessToken();
    return R.ok(accountService.removeToken(accessToken), "用户退出");
  }

  @PostMapping(value = "/status")
  public R<Object> status() {
    return R.ok(accountService.isLogin(), "获取登录状态");
  }
}
