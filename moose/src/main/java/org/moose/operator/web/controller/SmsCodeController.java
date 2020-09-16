package org.moose.operator.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.model.params.SmsCodeParam;
import org.moose.operator.web.service.impl.DefaultSmsCodeSenderServiceImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-16 14:32:14:32
 * @see org.moose.operator.web.controller
 */

@RestController
@RequestMapping(value = "/api/v1/sms")
@Slf4j
public class SmsCodeController {

  @Resource
  DefaultSmsCodeSenderServiceImpl smsCodeSenderService;

  @PostMapping("/send")
  public ResponseResult<Object> sendSmsCode(@Valid SmsCodeParam smsCodeParam,
      BindingResult result) {
    return smsCodeSenderService.sendSmsCode(smsCodeParam);
  }
}
