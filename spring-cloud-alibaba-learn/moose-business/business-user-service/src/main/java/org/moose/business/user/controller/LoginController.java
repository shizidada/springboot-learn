package org.moose.business.user.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.business.user.model.params.LoginParam;
import org.moose.business.user.service.LoginService;
import org.moose.commons.base.dto.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:10
 * @see org.moose.business.user.controller
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

  @Resource
  private LoginService loginService;

  @PostMapping("/login")
  public ResponseResult<?> login(
      @RequestBody @Valid LoginParam loginParam, BindingResult loginResult
  ) {
    return loginService.login(loginParam);
  }
}
