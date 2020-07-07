package org.moose.business.user.web.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.business.user.web.service.LogoutService;
import org.moose.commons.base.dto.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:11
 * @see org.moose.business.user.web.controller
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class LogoutController {

  @Resource
  private LogoutService logoutService;

  @PostMapping("/logout")
  public ResponseResult<?> logout(
      @RequestParam(value = "accessToken") String accessToken
  ) {
    return logoutService.logout(accessToken);
  }
}
