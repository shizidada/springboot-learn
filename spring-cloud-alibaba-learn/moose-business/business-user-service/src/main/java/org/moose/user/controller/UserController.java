package org.moose.user.controller;

import java.util.Date;
import javax.annotation.Resource;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.oauth.feign.OAuth2LoginFeignApi;
import org.moose.user.dto.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RestController(value = "user")
public class UserController {

  @Resource
  private OAuth2LoginFeignApi oAuth2LoginFeignApi;

  @GetMapping("/login")
  public ResponseResult<UserInfo> info() {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(658545465487979L);
    userInfo.setUsername("热情往来");
    userInfo.setBirthday(new Date());

    // for test
    //if (true) {
    //  throw new BusinessException("抛出来一个自定义 ... ", 100000);
    //}

    String accountName = "admin";
    String password = "123456";

    String login = oAuth2LoginFeignApi.login(accountName, password);

    //log.info("receive data from business-oauth2-service [{}]", login);
    return new ResponseResult<>(userInfo);
  }

  @GetMapping("/register")
  public ResponseResult<UserInfo> register() {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(658545465487979L);
    userInfo.setUsername("热情往来");
    userInfo.setBirthday(new Date());
    return new ResponseResult<>(userInfo);
  }



  @PostMapping("/echo/{message}")
  public String echo(
      @PathVariable("message") String message) {
    return "Echo " + message;
  }
}
