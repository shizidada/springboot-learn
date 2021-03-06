package org.moose.business.im.websocket.support;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-08 16:33:16:33
 * @see org.moose.business.im.websocket.support
 */
@Slf4j
@Component
public class PushMessageHandshakeInterceptor implements HandshakeInterceptor {

  @Override public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    log.info("PushMessageHandshakeInterceptor #开始握手！ ");
    return true;
  }

  @Override public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Exception exception) {
    log.info("PushMessageHandshakeInterceptor #握手完成！ ");
  }
}
