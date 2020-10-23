package org.moose.operator.web.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.R;
import org.moose.operator.model.vo.AuthTokenVO;
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
  public R<Object> getRefreshToken(AuthTokenVO tokenParam) {
    return R.ok(accountService.getRefreshTokenByAccessToken(tokenParam),
        "获取 refresh token");
  }

  @PostMapping(value = "/refreshToken")
  public R<Object> refreshToken(AuthTokenVO tokenParam) {
    return R.ok(accountService.getAccessTokenByRefreshToken(tokenParam),
        "获取 access token");
  }
}
