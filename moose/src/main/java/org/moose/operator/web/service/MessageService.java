package org.moose.operator.web.service;

import com.mongodb.client.result.UpdateResult;
import org.moose.operator.mongo.entity.Message;

/**
 * @author taohua
 */
public interface MessageService {

  /**
   * 保存消息
   *
   * @param message #Message
   * @return Message
   */
  Message saveMessage(Message message);

  /**
   * 跟新消息状态
   *
   * @param messageId 消息 ID
   * @param status    消息状态
   * @return UpdateResult
   */
  UpdateResult updateMessageState(String messageId, Integer status);
}
