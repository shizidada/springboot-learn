package org.moose.business.api.service;

import org.moose.business.api.model.dto.MessageDTO;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-08 14:37:14:37
 * @see org.moose.business.api.service
 */
public interface MessageService {

  /**
   * 保存消息
   *
   * @param message 消息
   *
   * @return MessageDO
   */
  MessageDTO saveMessage(MessageDTO message);

  /**
   * 更新消息状态
   *
   * @param message 消息
   *
   * @return 是否更新成功
   */
  int updateMessageStatus(MessageDTO message);
}
