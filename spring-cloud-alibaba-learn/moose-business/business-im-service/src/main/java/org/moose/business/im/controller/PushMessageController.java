package org.moose.business.im.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.business.im.websocket.handler.PushMessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

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
@Slf4j
@RestController
public class PushMessageController {

  @Resource
  private PushMessageHandler pushMessageHandler;

  @GetMapping(value = "/push/message")
  public void pushMessage() {
    boolean pushSuccess = pushMessageHandler.sendMessageToAllUser(new TextMessage("push test message"));
    log.info("pushSuccess {} ", pushSuccess);
  }
}
