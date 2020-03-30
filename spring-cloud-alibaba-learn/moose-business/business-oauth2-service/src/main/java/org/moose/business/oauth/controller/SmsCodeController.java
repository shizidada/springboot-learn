package org.moose.business.oauth.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
public class SmsCodeController {

  @GetMapping(value = "/code/sms", produces = MediaType.APPLICATION_JSON_VALUE)
  public String sendSmdCode() {
    String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
    return code;
  }
}
