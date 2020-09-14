package org.moose.operator.web.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.web.service.impl.AccountServiceImpl;
import org.moose.operator.web.service.impl.LoginServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-30 23:15:23:15
 * @see org.moose.operator.web.controller
 */

@RestController
@RequestMapping(value = "/api/v1/login")
@Slf4j
public class LoginController {

  @Resource
  private AccountServiceImpl accountService;

  @Resource
  private LoginServiceImpl loginService;

  @PostMapping(value = "/status")
  public ResponseResult<Object> status() {
    return loginService.isLogin();
  }
}
