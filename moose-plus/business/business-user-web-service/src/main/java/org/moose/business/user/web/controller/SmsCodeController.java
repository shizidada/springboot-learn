package org.moose.business.user.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.moose.business.user.web.model.params.SmsCodeParam;
import org.moose.business.user.web.service.SmsCodeService;
import org.moose.commons.base.dto.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @see org.moose.business.user.web.controller
 */

@RestController
@RequestMapping("/user")
public class SmsCodeController {

  @Resource
  private SmsCodeService smsCodeService;

  @PostMapping(value = "/sms/send")
  public ResponseResult<?> send(
      @RequestBody @Valid SmsCodeParam smsCodeParam, BindingResult bindingResult) {
    return smsCodeService.sendSmsCode(smsCodeParam);
  }
}
