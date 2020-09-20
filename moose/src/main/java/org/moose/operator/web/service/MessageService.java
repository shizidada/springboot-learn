package org.moose.operator.web.service;

import com.mongodb.client.result.UpdateResult;
import org.moose.operator.model.dto.ChatMessageDTO;

/**
 * @author taohua
 */
public interface MessageService {

  /**
   * 保存消息
   *
   * @param chatMessageDTO #Message
   * @return Message
   */
  ChatMessageDTO saveMessage(ChatMessageDTO chatMessageDTO);

  /**
   * 跟新消息状态
   *
   * @param messageId 消息 ID
   * @param status    消息状态
   * @return UpdateResult
   */
  UpdateResult updateMessageStatus(String messageId, Integer status);
}
