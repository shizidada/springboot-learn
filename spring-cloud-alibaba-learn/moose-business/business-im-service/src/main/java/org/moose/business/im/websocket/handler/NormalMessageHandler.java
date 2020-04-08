//package org.moose.business.im.websocket.handler;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import javax.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.MessageModel;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.moose.business.api.model.dto.MessageDTO;
//import org.moose.business.api.service.MessageService;
//import org.moose.commons.base.snowflake.SnowflakeIdWorker;
//import org.moose.provider.account.model.dto.AccountDTO;
//import org.moose.provider.account.service.AccountService;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
///**
// * 普通文本消息处理器
// * <p>
// * Description:
// * </p>
// *
// * @author taohua
// * @version v1.0.0
// * @date 2020-04-08 14:31:14:31
// * @see org.moose.business.im.websocket.handler
// */
//@Component
//@Slf4j
//@RocketMQMessageListener(topic = "${mq.im.topic}", consumerGroup = "${mq.im.consumer.group}", messageModel = MessageModel.BROADCASTING)
//public class NormalMessageHandler extends TextWebSocketHandler implements RocketMQListener<String> {
//
//  @Resource
//  private SnowflakeIdWorker snowflakeIdWorker;
//
//  @Resource
//  private MessageService messageService;
//
//  @Resource
//  private AccountService accountService;
//
//  @Resource
//  private RocketMQTemplate rocketMQTemplate;
//
//  private static final ObjectMapper MAPPER = new ObjectMapper();
//
//  /**
//   * 用户存放用户的在线 session
//   */
//  private static final Map<Long, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();
//
//  /**
//   * 开启连接
//   *
//   * @throws Exception
//   */
//  @Override
//  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//    // 将当前用户的 session 放置到 map 中，后面会使用相应的 session 通信
//    String accountId = (String) session.getAttributes().get("accountId");
//    log.info("用户上线了 {} ", accountId);
//    Long currentAccountId = Long.valueOf(accountId);
//    SESSION_MAP.put(currentAccountId, session);
//  }
//
//  @Override
//  protected void handleTextMessage(WebSocketSession session, TextMessage textMessage)
//      throws Exception {
//    String accountId = (String) session.getAttributes().get("accountId");
//    Long fromAccountId = Long.valueOf(accountId);
//    AccountDTO fromUser = accountService.getAccountByAccountId(fromAccountId);
//    //TODO: 不存在发送用户
//    if (fromUser == null) {
//      // 抛出异常
//    }
//    // 将消息序列化成JSON串
//    JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
//    Long toAccountId = jsonNode.get("toId").asLong();
//    AccountDTO toUser = accountService.getAccountByAccountId(toAccountId);
//    //TODO: 不存在接受用户
//    if (toUser == null) {
//      // 抛出异常
//    }
//
//    //TODO: 判断是否为好友
//    String message = jsonNode.get("message").asText();
//
//    // 构造消息对象
//    long messageId = snowflakeIdWorker.nextId();
//    MessageDTO messageDTO = new MessageDTO();
//    messageDTO.setMessageId(messageId);
//    messageDTO.setFrom(fromUser);
//    messageDTO.setTo(toUser);
//    messageDTO.setMessage(message);
//    messageDTO.setMessageStatus(1);
//    messageDTO.setSendTime(LocalDateTime.now());
//    messageDTO.setReadTime(LocalDateTime.now());
//    messageDTO.setCreateTime(LocalDateTime.now());
//    messageDTO.setUpdateTime(LocalDateTime.now());
//
//    // TODO: 将消息保存到数据库中 mongodb or mysql ???
//    messageDTO = this.messageService.saveMessage(messageDTO);
//
//    // TODO: 根据前端的格式对接
//    String messageJson = MAPPER.writeValueAsString(messageDTO);
//    // 判断 to 用户是否在线
//    WebSocketSession socketSession = SESSION_MAP.get(toAccountId);
//    if (socketSession != null && socketSession.isOpen()) {
//      // 响应消息
//      socketSession.sendMessage(new TextMessage(messageJson));
//
//      // 更改消息状态
//      messageDTO.setMessageId(messageId);
//      messageDTO.setMessageStatus(2);
//      messageDTO.setReadTime(LocalDateTime.now());
//      this.messageService.updateMessageStatus(messageDTO);
//    } else {
//      // TODO: 不在线用户
//      // 怎么解决分布式部署 websocket ??? 该用户可能下线，可能在其他节点，发送消息到MQ系统
//      this.rocketMQTemplate.convertAndSend("IM-SEND-MESSAGE-TOPIC:SEND_MESSAGE", messageJson);
//    }
//  }
//
//  /**
//   * 断开连接
//   *
//   * @throws Exception
//   */
//  @Override
//  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//    // 用户断开移除用户 Session
//    String accountId = (String) session.getAttributes().get("accountId");
//    log.info("用户下线了 {} ", accountId);
//    Long currentAccountId = Long.valueOf(accountId);
//    SESSION_MAP.remove(currentAccountId);
//  }
//
//  /**
//   * rocketMQ 消息
//   *
//   * @param message 消息体
//   */
//  @Override public void onMessage(String message) {
//    log.info("接收到 IM 消息 {} ", message);
//    try {
//      JsonNode jsonNode = MAPPER.readTree(message);
//      String toId = jsonNode.get("to").get("accountId").toString();
//      // 判断其他机器上的 to 用户是否在线
//      WebSocketSession toSession = SESSION_MAP.get(toId);
//      if (toSession != null && toSession.isOpen()) {
//        //TODO 具体格式需要和前端对接
//        toSession.sendMessage(new TextMessage(message));
//        //更新消息状态为已读
//        MessageDTO messageDTO = new MessageDTO();
//        long messageId = jsonNode.get("id").asLong();
//        messageDTO.setMessageId(messageId);
//        messageDTO.setMessageStatus(2);
//        messageDTO.setReadTime(LocalDateTime.now());
//        messageDTO.setUpdateTime(LocalDateTime.now());
//        this.messageService.updateMessageStatus(messageDTO);
//      } else {
//        // 不需要处理
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }
//}
