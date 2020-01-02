package org.excel.operator.websocket.config;

import org.excel.operator.websocket.handler.MessageHandler;
import org.excel.operator.websocket.interceptor.MessageHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author taohua
 */
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

  @Autowired
  private MessageHandler messageHandler;

  @Autowired
  private MessageHandshakeInterceptor messageHandshakeInterceptor;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(this.messageHandler, "/ws/{userId}")
        .setAllowedOrigins("*")
        .addInterceptors(this.messageHandshakeInterceptor);
  }
}
