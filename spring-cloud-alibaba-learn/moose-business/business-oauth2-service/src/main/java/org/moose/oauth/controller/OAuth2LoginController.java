package org.moose.oauth.controller;

import org.moose.commons.base.dto.ResponseResult;
import org.moose.oauth.dto.LoginInfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权登录
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-13 13:15:13:15
 * @see org.moose.oauth.controller
 */
@RestController("/oauth")
public class OAuth2LoginController {

  @GetMapping("/login")
  public ResponseResult<LoginInfoDTO> login() {
    return new ResponseResult<>(null);
  }

  @GetMapping("/info")
  public ResponseResult<LoginInfoDTO> info() {
    LoginInfoDTO loginInfo = new LoginInfoDTO();
    loginInfo.setName("张世豪");
    loginInfo.setAvatar(
        "https://huyaimg.msstatic.com/avatar/1021/38/798015db445aa31accd2fb80a80f10_180_135.jpg?440020");
    loginInfo.setNickName("阿豪");
    return new ResponseResult<>(loginInfo);
  }
}
