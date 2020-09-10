package org.excel.operator.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.model.dto.RegisterInfoDTO;
import org.excel.operator.model.params.AuthTokenParam;
import org.excel.operator.model.params.LoginParam;
import org.excel.operator.web.service.impl.AccountServiceImpl;
import org.excel.operator.web.service.impl.LoginServiceImpl;
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

  @Resource
  private LoginServiceImpl loginService;

  /**
   * spring security to login
   *
   * @PostMapping(value = "/login")
   */
  @PostMapping(value = "/login")
  public ResponseResult<Object> login(@RequestBody @Valid LoginParam loginParam,
      BindingResult result) {
    return loginService.login(loginParam);
  }

  @PostMapping(value = "/getRefreshToken")
  public ResponseResult<Object> getRefreshToken(@RequestBody AuthTokenParam tokenParam) {
    String accessToken = tokenParam.getAccessToken();
    return loginService.getRefreshTokenByAccessToken(accessToken);
  }

  @PostMapping(value = "/refreshToken")
  public ResponseResult<Object> refreshToken(@RequestBody AuthTokenParam tokenParam) {
    String refreshToken = tokenParam.getRefreshToken();
    return loginService.getAccessTokenByRefreshToken(refreshToken);
  }

  /**
   * spring security to register
   */
  @PostMapping(value = "/register")
  public ResponseResult<Object> register(@Valid RegisterInfoDTO registerInfoDTO,
      BindingResult result,
      HttpServletRequest request) {
    return accountService.register(request, registerInfoDTO);
  }

  @PostMapping(value = "/info")
  public ResponseResult<Object> info() {
    return accountService.getAccountInfo();
  }
}
