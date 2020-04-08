package org.moose.business.im.controller;

import javax.annotation.Resource;
import org.moose.business.api.service.PushMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-08 16:25:16:25
 * @see org.moose.business.im.controller
 */
@RestController
public class PushMessageController {

  @Resource
  private PushMessageService pushMessageService;

  @GetMapping(value = "/push/message")
  public void pushMessage() {
    pushMessageService.pushMessage();
  }
}
