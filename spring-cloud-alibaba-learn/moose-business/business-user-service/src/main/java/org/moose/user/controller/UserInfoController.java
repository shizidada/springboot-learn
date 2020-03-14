package org.moose.user.controller;

import java.util.Date;
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
@RestController
public class UserInfoController {

  @GetMapping("/user/info")
  public UserInfo info() {
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(658545465487979L);
    userInfo.setUsername("热情往来");
    userInfo.setBirthday(new Date());
    return userInfo;
  }
}
