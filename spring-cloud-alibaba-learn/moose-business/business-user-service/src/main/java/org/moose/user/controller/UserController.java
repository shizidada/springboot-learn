package org.moose.user.controller;

import org.moose.account.service.AccountService;
import org.moose.account.service.AuthService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
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
public class UserController {

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Reference(version = "1.0.0")
  private AuthService authService;

  @PostMapping("/api/v1/user/login")
  public String login(
      @RequestParam("accountName") String accountName,
      @RequestParam("password") String password) {
    String result = accountService.login(accountName, password);
    return "Account Service : " + result;
  }

  @PostMapping("/api/v1/user/getAuthUrl")
  public String getAuthUrl(
      @RequestParam("type") String type) {
    String result = authService.getAuthUrl(type);
    return "Auth Service : " + result;
  }
}
