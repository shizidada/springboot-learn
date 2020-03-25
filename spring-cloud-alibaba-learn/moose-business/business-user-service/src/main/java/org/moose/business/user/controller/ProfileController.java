package org.moose.business.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.moose.business.user.model.vo.ProfileInfoVo;
import org.moose.commons.base.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-25 13:16:13:16
 * @see org.moose.business.user.controller
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class ProfileController {

  @GetMapping("/profile/info")
  public ResponseResult<?> info() {
    ProfileInfoVo infoVo = new ProfileInfoVo();
    infoVo.setAccountName("原来你什么都不要");
    infoVo.setAvatar("http://icon.org");
    infoVo.setPhone("17329839132");
    return new ResponseResult<>(infoVo);
  }
}
