package org.moose.user.controller;

import java.util.Date;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.exception.BusinessException;
import org.moose.oauth.feign.OAuth2LoginFeignApi;
import org.moose.user.dto.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-13 13:30:13:30
 * @see org.moose.user.controller
 */
@Slf4j
@RestController(value = "user")
public class InfoController {

  @Resource
  private OAuth2LoginFeignApi auth2LoginFeignApi;

  @GetMapping("/info")
  public ResponseResult<UserInfo> info() {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(658545465487979L);
    userInfo.setUsername("热情往来");
    userInfo.setBirthday(new Date());

    // for test
    if (true) {
      throw new BusinessException("抛出来一个自定义 ... ", 100000);
    }

    String login = auth2LoginFeignApi.login();
    log.info("receive data from business-oauth2-service [{}]", login);
    return new ResponseResult<>(userInfo);
  }
}
