package org.moose.operator.web.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.model.params.AuthTokenParam;
import org.moose.operator.web.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taohua
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/token")
public class TokenController {

  @Resource
  private AccountService accountService;

  @PostMapping(value = "/getRefreshToken")
  public ResponseResult<Object> getRefreshToken(AuthTokenParam tokenParam) {
    String accessToken = tokenParam.getAccessToken();
    return new ResponseResult<>(accountService.getRefreshTokenByAccessToken(accessToken),
        "获取 refresh token");
  }

  @PostMapping(value = "/refreshToken")
  public ResponseResult<Object> refreshToken(AuthTokenParam tokenParam) {
    String refreshToken = tokenParam.getRefreshToken();
    return new ResponseResult<>(accountService.getAccessTokenByRefreshToken(refreshToken),
        "获取 access token");
  }
}
