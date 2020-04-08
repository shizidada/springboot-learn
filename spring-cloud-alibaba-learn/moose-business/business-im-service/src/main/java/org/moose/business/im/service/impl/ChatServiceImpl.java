package org.moose.business.im.service.impl;

import javax.annotation.Resource;
import org.moose.business.api.model.dto.ChatMessageDTO;
import org.moose.business.api.service.ChatService;
import org.moose.business.im.websocket.handler.PushMessageHandler;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/8 21:27
 * @see org.moose.business.im.service.impl
 */
public class ChatServiceImpl implements ChatService {

  @Resource
  private PushMessageHandler pushMessageHandler;

  @Override public ChatMessageDTO saveMessage(ChatMessageDTO message) {
    return null;
  }

  @Override public int updateMessageStatus(ChatMessageDTO message) {
    return 0;
  }
}
