package org.excel.operator.web.websocket;

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
public class WebSocketConfig implements WebSocketConfigurer {

  @Autowired
  private MessageWebSocketHandler messageWebSocketHandler;

  @Autowired
  private MessageHandshakeInterceptor messageHandshakeInterceptor;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(this.messageWebSocketHandler, "/socket.io/{userId}")
        .setAllowedOrigins("*")
        .addInterceptors(this.messageHandshakeInterceptor);
  }
}
