package org.moose.business.user.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.moose.business.user.model.params.SmsCodeParam;
import org.moose.business.user.service.UserService;
import org.moose.commons.base.dto.ResponseResult;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-30 16:04:16:04
 * @see org.moose.business.oauth.controller
 */

@RestController
@RequestMapping("/sms")
public class SmsCodeController {

  @Resource
  private UserService userService;

  @GetMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseResult<?> send(
      @RequestBody @Valid SmsCodeParam smsCodeParam, BindingResult bindingResult) {
    return userService.sendSmsCode(smsCodeParam);
  }
}
