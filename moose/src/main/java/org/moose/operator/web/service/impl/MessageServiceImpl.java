package org.moose.operator.web.service.impl;

import com.mongodb.client.result.UpdateResult;
import java.time.LocalDateTime;
import org.apache.commons.lang3.ObjectUtils;
import org.moose.operator.constant.ChatMessageConstants;
import org.moose.operator.model.domain.ChatMessageDO;
import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.ChatMessageDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.web.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author taohua
 */
@Service
public class MessageServiceImpl implements MessageService {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override public ChatMessageDTO saveMessage(ChatMessageDTO chatMessageDTO) {
    ChatMessageDO chatMessageDO = new ChatMessageDO();
    BeanUtils.copyProperties(chatMessageDTO, chatMessageDO);
    UserInfoDTO fromUserInfoDTO = chatMessageDTO.getFrom();
    if (ObjectUtils.isNotEmpty(fromUserInfoDTO)) {
      UserInfoDO fromUserInfoDO = new UserInfoDO();
      BeanUtils.copyProperties(fromUserInfoDTO, fromUserInfoDO);
      chatMessageDO.setFrom(fromUserInfoDO);
    }

    UserInfoDTO toUserInfoDTO = chatMessageDTO.getTo();
    if (ObjectUtils.isNotEmpty(toUserInfoDTO)) {
      UserInfoDO toUserInfoDO = new UserInfoDO();
      BeanUtils.copyProperties(toUserInfoDTO, toUserInfoDO);
      chatMessageDO.setTo(toUserInfoDO);
    }

    chatMessageDO = this.mongoTemplate.save(chatMessageDO);
    if (ObjectUtils.isEmpty(chatMessageDO)) {
      return null;
    }
    return chatMessageDTO;
  }

  /**
   * 根据id更新会话是否已读状态
   */
  @Override
  public UpdateResult updateMessageStatus(String messageId, Integer status) {
    Query query = Query.query(Criteria.where("message_id").is(messageId));
    Update update = Update.update("is_read", status);
    if (ChatMessageConstants.MESSAGE_UN_READ.equals(status)) {
      // 更新发送时间
      update.set("send_time", LocalDateTime.now());
    } else if (ChatMessageConstants.MESSAGE_IS_READ.equals(status)) {
      // 更新阅读时间
      update.set("read_time", LocalDateTime.now());
    }
    return this.mongoTemplate.updateFirst(query, update, ChatMessageDO.class);
  }
}
