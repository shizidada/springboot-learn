package org.moose.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.moose.account.model.dto.AccountDTO;
import org.moose.account.model.dto.PasswordDTO;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.oauth.feign.OAuth2LoginFeignApi;
import org.moose.user.dto.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/v1/user")
public class UserController {

  @Resource
  private OAuth2LoginFeignApi oAuth2LoginFeignApi;

  @PostMapping("/register")
  public ResponseResult<Map<String, Object>> register(
      @RequestBody AccountDTO accountDTO,
      @RequestBody PasswordDTO passwordDTO) {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(658545465487979L);
    userInfo.setUsername("热情往来");
    userInfo.setBirthday(new Date());

    Map<String, Object> map = new HashMap<>(16);
    map.put("account", accountDTO);
    map.put("password", passwordDTO);
    map.put("userInfo", userInfo);
    return new ResponseResult<>(map);
  }

  @GetMapping("/info")
  public ResponseResult<UserInfo> info() {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(658545465487979L);
    userInfo.setUsername("张世豪");
    userInfo.setAvatar(
        "https://huyaimg.msstatic.com/avatar/1021/38/798015db445aa31accd2fb80a80f10_180_135.jpg?440020");
    userInfo.setNickName("阿豪");
    userInfo.setBirthday(new Date());

    // for test
    //if (true) {
    //  throw new BusinessException("抛出来一个自定义 ... ", 100000);
    //}
    //String login = oAuth2LoginFeignApi.login(accountName, password);
    //log.info("receive data from business-oauth2-service [{}]", login);

    return new ResponseResult<>(userInfo);
  }

  @PostMapping("/echo/{message}")
  public String echo(
      @PathVariable("message") String message) {
    return "Echo " + message;
  }
}
