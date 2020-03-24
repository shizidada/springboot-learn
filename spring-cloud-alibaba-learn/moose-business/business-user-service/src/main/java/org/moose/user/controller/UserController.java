package org.moose.user.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.oauth.feign.OAuth2RequestTokenApi;
import org.moose.user.model.params.LoginParam;
import org.moose.user.model.params.RegisterParam;
import org.moose.user.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/8 14:09
 * @see org.moose.user.controller
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

  @Resource
  private OAuth2RequestTokenApi oAuth2RequestTokenApi;

  @Resource
  private UserService userService;

  @PostMapping("/register")
  public ResponseResult<?> register(
      @RequestBody RegisterParam registerParam, BindingResult bindingResult) {
    return userService.register(registerParam);
  }

  @PostMapping("/login")
  public ResponseResult<?> login(
      @RequestBody @Valid LoginParam loginParam, BindingResult bindingResult
  ) {
    return userService.login(loginParam);
  }

  @PostMapping("/logout")
  public ResponseResult<?> logout(
      @RequestParam(value = "accessToken") String accessToken
  ) {
    return userService.logout(accessToken);
  }

  @GetMapping("/info")
  public ResponseResult<?> info() {
    return new ResponseResult<>(null);
  }
}
