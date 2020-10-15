package org.moose.operator.web.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.moose.operator.constant.ChatMessageConstants;
import org.moose.operator.model.dto.ChatMessageDTO;
import org.moose.operator.model.dto.MessageInfoDTO;
import org.moose.operator.model.dto.UserInfoDTO;
import org.moose.operator.util.MapperUtils;
import org.moose.operator.web.service.MessageService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author taohua
 */
@Slf4j
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  /**
   * 用户存放用户的在线 session
   */
  private static final Map<String, WebSocketSession> SESSION_MAP = new HashMap<>();

  @Autowired
  private UserInfoService userService;

  @Autowired
  private MessageService messageService;

  /**
   * 开启连接
   *
   * @throws Exception
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    // 将当前用户的 session 放置到 map 中，后面会使用相应的 session 通信
    String userId = (String) session.getAttributes().get("userId");
    SESSION_MAP.put(userId, session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage textMessage)
      throws Exception {
    try {

      //Principal principal = session.getPrincipal();

      // 将消息序列化成JSON串
      String messagePayloadJson = textMessage.getPayload();
      if (StringUtils.isEmpty(messagePayloadJson)) {
        log.info("parse message payload json is empty :: [{}]", messagePayloadJson);
        return;
      }

      MessageInfoDTO messageInfo = MapperUtils.json2pojo(messagePayloadJson, MessageInfoDTO.class);
      if (ObjectUtils.isEmpty(messageInfo)) {
        log.info("parse message info is empty :: [{}]", messageInfo);
        return;
      }

      // TODO: check toId, the user is exist
      String toUserId = messageInfo.getToId();
      String message = messageInfo.getMessage();
      if (StringUtils.isEmpty(toUserId) || StringUtils.isEmpty(message)) {
        log.info("parse toId is empty or message is empty :: [{}: {}]", toUserId, message);
        return;
      }

      UserInfoDTO toUser = userService.getUserInfoByUserId(toUserId);
      if (ObjectUtils.isEmpty(toUser)) {
        log.info("parse toUser is empty :: [{}]", messageInfo);
        return;
      }

      String userId = (String) session.getAttributes().get("userId");
      UserInfoDTO fromUser = userService.getUserInfoByUserId(userId);
      if (ObjectUtils.isEmpty(fromUser)) {
        log.info("parse fromUser is empty :: [{}]", messageInfo);
        return;
      }

      // 构造消息对象
      ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
      chatMessageDTO.setMessageId(ObjectId.get().toHexString());
      chatMessageDTO.setFrom(fromUser);
      chatMessageDTO.setTo(toUser);
      chatMessageDTO.setMessage(message);
      chatMessageDTO.setIsRead(ChatMessageConstants.MESSAGE_UN_READ);
      chatMessageDTO.setSendTime(LocalDateTime.now());

      // 将消息保存到 mongodb 数据库中
      log.info("the chat message :: [{}]", chatMessageDTO);

      chatMessageDTO = this.messageService.saveMessage(chatMessageDTO);
      if (ObjectUtils.isEmpty(chatMessageDTO)) {
        log.info("save chat message fail :: [{}]", chatMessageDTO);
        return;
      }
      // 判断 to 用户是否在线
      WebSocketSession socketSession = SESSION_MAP.get(toUserId);
      if (ObjectUtils.isNotEmpty(socketSession) && socketSession.isOpen()) {
        // TODO: 根据前端的格式对接
        // 响应消息
        socketSession.sendMessage(new TextMessage(MapperUtils.obj2json(chatMessageDTO)));

        // 更改消息状态
        this.messageService.updateMessageStatus(chatMessageDTO.getMessageId(),
            ChatMessageConstants.MESSAGE_IS_READ);
      }
    } catch (Exception e) {
      log.error("send message error: ", e);
    }
  }

  /**
   * 断开连接
   *
   * @throws Exception
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    String userId = (String) session.getAttributes().get("userId");
    // 用户断开移除用户 Session
    SESSION_MAP.remove(userId);
  }
}
