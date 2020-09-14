package org.moose.operator.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.model.dto.RegisterInfoDTO;
import org.moose.operator.model.params.AuthTokenParam;
import org.moose.operator.model.params.LoginParam;
import org.moose.operator.web.service.AccountService;
import org.moose.operator.web.service.LoginService;
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
  private AccountService accountService;

  @Resource
  private LoginService loginService;

  @Resource
  private ObjectMapper objectMapper;

  /**
   * spring security oauth2.0 to login
   */
  @PostMapping(value = "/login", consumes = "application/json")
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
   * spring security oauth2.0 to register
   */
  @PostMapping(value = "/register", consumes = "application/json")
  public ResponseResult<Object> register(@Valid RegisterInfoDTO registerInfoDTO,
      BindingResult result,
      HttpServletRequest request) {
    return accountService.saveAccount(request, registerInfoDTO);
  }

  @PostMapping(value = "/info")
  public ResponseResult<Object> info() {
    return accountService.getAccountInfo();
  }
}
