package org.moose.business.im.websocket.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-08 16:07:16:07
 * @see org.moose.business.im.websocket.handler
 */
@Slf4j
@Component
public class PushMessageHandler extends TextWebSocketHandler {

  private static final Map<Long, WebSocketSession> SESSION_MAP;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  static {
    SESSION_MAP = new ConcurrentHashMap<>();
  }

  @Override public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    super.afterConnectionEstablished(session);
    // 将当前用户的 session 放置到 map 中，后面会使用相应的 session 通信
    long id = snowflakeIdWorker.nextId();
    SESSION_MAP.put(id, session);
    log.info("PushMessageHandler #afterConnectionEstablished {}", session);
  }

  @Override public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {
    super.handleMessage(session, message);
    log.info("PushMessageHandler #handleMessage {} {}", session, message);
  }

  @Override protected void handleTextMessage(WebSocketSession session, TextMessage message)
      throws Exception {
    super.handleTextMessage(session, message);
    log.info("PushMessageHandler #handleTextMessage {} {}", session, message);
  }

  @Override protected void handlePongMessage(WebSocketSession session, PongMessage message)
      throws Exception {
    super.handlePongMessage(session, message);
    log.info("PushMessageHandler #handlePongMessage {} {}", session, message);
  }

  @Override public void handleTransportError(WebSocketSession session, Throwable exception)
      throws Exception {
    super.handleTransportError(session, exception);
    log.info("PushMessageHandler #handleTransportError {} {}", session, exception);
  }

  @Override public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
      throws Exception {
    super.afterConnectionClosed(session, status);
    log.info("PushMessageHandler #afterConnectionClosed {} {}", session, status);
  }

  @Override public boolean supportsPartialMessages() {
    log.info("PushMessageHandler #supportsPartialMessages");
    return super.supportsPartialMessages();
  }

  public boolean sendMessageToAllUser(TextMessage message) {
    boolean isSendSuccess = true;
    Set<Long> dunIds = SESSION_MAP.keySet();
    WebSocketSession session = null;
    for (Long dunId : dunIds) {
      try {
        session = SESSION_MAP.get(dunId);
        if (session.isOpen()) {
          session.sendMessage(message);
        }
      } catch (IOException e) {
        e.printStackTrace();
        isSendSuccess = false;
      }
    }
    return isSendSuccess;
  }
}
