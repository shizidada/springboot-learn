package org.moose.business.user.web.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.business.user.web.service.ProfileService;
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
 * @see org.moose.business.user.web.controller
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class ProfileController {

  @Resource
  private ProfileService userProfileService;

  @GetMapping("/profile/info")
  public ResponseResult<?> info() {
    return userProfileService.getProfileInfo();
  }
}
