package org.excel.operator.web.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import org.excel.operator.mongo.entity.Message;
import org.excel.operator.mongo.entity.User;
import org.excel.operator.web.service.MessageService;
import org.excel.operator.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author taohua
 */
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

  @Autowired
  private MessageService messageService;

  @Autowired
  private UserInfoService userInfoService;

  private static final ObjectMapper MAPPER = new ObjectMapper();

  // 用户存放用户的在线 session
  private static final Map<String, WebSocketSession> SESSION_MAP = new HashMap<>();

  /**
   * 开启连接
   *
   * @throws Exception
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    // 将当前用户的session放置到map中，后面会使用相应的session通信
    String userId = (String) session.getAttributes().get("uid");
    SESSION_MAP.put(userId, session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage textMessage)
      throws Exception {
    String userId = (String) session.getAttributes().get("uid");
    // 将消息序列化成JSON串
    JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
    String toId = jsonNode.get("toId").asText();
    String msg = jsonNode.get("msg").asText();

    User fromUser = userInfoService.getUser(userId);
    User toUser = userInfoService.getUser(toId);

    // 构造消息对象
    Message message = Message.builder()
        .messageId(ObjectId.get().toHexString())
        .from(fromUser)
        .to(toUser)
        .msg(msg)
        .isRead(1)
        .sendDate(new Date())
        .readDate(new Date())
        .build();
    //将消息保存到mongodb数据库中
    message = this.messageService.saveMessage(message);
    // 判断 to 用户是否在线
    WebSocketSession socketSession = SESSION_MAP.get(toId);
    if (socketSession != null && socketSession.isOpen()) {
      // TODO 根据前端的格式对接
      // 响应消息
      socketSession.sendMessage(new TextMessage(MAPPER.writeValueAsString(message)));

      // 更改消息状态
      this.messageService.updateMessageState(message.getMessageId(), 2);
    }
  }

  /**
   * 断开连接
   *
   * @throws Exception
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    String uid = (String) session.getAttributes().get("uid");
    // 用户断开移除用户Session
    SESSION_MAP.remove(uid);
  }
}
