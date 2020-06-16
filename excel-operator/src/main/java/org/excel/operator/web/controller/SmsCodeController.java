package org.excel.operator.web.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.web.security.sms.DefaultSmsCodeSender;
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
 * @see org.excel.operator.web.controller
 */

@RestController
@RequestMapping(value = "/api/v1/sms")
@Slf4j
public class SmsCodeController {

  @Resource
  DefaultSmsCodeSender smsCodeSender;

  @RequestMapping("/send")
  public ResponseResult<Boolean> sendSmsCode(String phone) {
    String smsCode = RandomStringUtils.randomNumeric(6);
    smsCodeSender.send(phone, smsCode);
    return new ResponseResult<Boolean>(true, "短信验证码发送成功");
  }
}
