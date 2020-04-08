package org.moose.business.im.service.impl;

import com.alibaba.fastjson.JSON;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.business.api.model.dto.PushMessageDTO;
import org.moose.business.api.service.PushMessageService;
import org.moose.business.im.websocket.handler.PushMessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/8 21:14
 * @see org.moose.business.im.service.impl
 */
@Slf4j
@Component
public class PushMessageServiceImpl implements PushMessageService {

  @Resource
  private PushMessageHandler pushMessageHandler;

  @Override
  public void pushMessage() {
    PushMessageDTO pushMessageDTO = new PushMessageDTO();
    pushMessageDTO.setTitle("消息标题");
    pushMessageDTO.setContent("消息内容消息内容消息内容消息内容消息内容消息内容");
    String jsonString = JSON.toJSONString(pushMessageDTO);
    boolean pushSuccess = pushMessageHandler.sendMessageToAllUser(new TextMessage(jsonString));
    log.info("pushSuccess {} ", pushSuccess);
  }
}
