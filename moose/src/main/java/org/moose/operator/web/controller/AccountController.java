package org.moose.operator.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

  @Resource
  private ObjectMapper objectMapper;

  /**
   * spring security oauth2.0 to register
   */
  @PostMapping(value = "/register")
  public ResponseResult<Object> register(@Valid RegisterInfoParam registerInfoParam,
      BindingResult result,
      HttpServletRequest request) {
    return accountService.saveAccount(request, registerInfoParam);
  }

  /**
   * spring security oauth2.0 to login
   */
  @PostMapping(value = "/login")
  public ResponseResult<Object> login(@Valid LoginParam loginParam,
      BindingResult result) {
    return accountService.getToken(loginParam);
  }

  /**
   * spring security oauth2.0 to logout
   */
  @PostMapping(value = "/logout")
  public ResponseResult<Object> logout(AuthTokenParam tokenParam) {
    String accessToken = tokenParam.getAccessToken();
    return accountService.removeToken(accessToken);
  }

  @PostMapping(value = "/getRefreshToken")
  public ResponseResult<Object> getRefreshToken(AuthTokenParam tokenParam) {
    String accessToken = tokenParam.getAccessToken();
    return accountService.getRefreshTokenByAccessToken(accessToken);
  }

  @PostMapping(value = "/refreshToken")
  public ResponseResult<Object> refreshToken(AuthTokenParam tokenParam) {
    String refreshToken = tokenParam.getRefreshToken();
    return accountService.getAccessTokenByRefreshToken(refreshToken);
  }

  @PostMapping(value = "/status")
  public ResponseResult<Object> status() {
    return accountService.isLogin();
  }

  @PostMapping(value = "/info")
  public ResponseResult<Object> info() {
    return accountService.getAccountInfo();
  }
}
