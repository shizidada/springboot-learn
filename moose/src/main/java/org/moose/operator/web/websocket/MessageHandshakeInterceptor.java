package org.moose.operator.web.websocket;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.constant.ChatMessageConstants;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author taohua
 */
@Component
@Slf4j
public class MessageHandshakeInterceptor implements HandshakeInterceptor {

  /**
   * 握手前
   *
   * @param serverHttpRequest
   * @param serverHttpResponse
   * @param webSocketHandler
   * @param map
   * @return
   * @throws Exception
   */
  @Override
  public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
      Map<String, Object> map) throws Exception {
    log.info("------ web socket before Handshake ------");
    // 处理请求路径为 ---> /socket.io/{userId}
    String path = serverHttpRequest.getURI().getPath();
    String[] split = StringUtils.split(path, "/");
    // 请求路径切割后不等于2的排除掉
    if (split.length != ChatMessageConstants.SOCKET_URL_PARAM_LENGTH) {
      return Boolean.FALSE;
    }
    // 第二个参数不是数字排除掉
    String userId = split[1];
    if (StringUtils.isEmpty(userId)) {
      return Boolean.FALSE;
    }

    map.put("userId", userId);
    return Boolean.TRUE;
  }

  /**
   * 握手后
   *
   * @param serverHttpRequest
   * @param serverHttpResponse
   * @param webSocketHandler
   * @param e
   */
  @Override
  public void afterHandshake(ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    log.info("------ web socket after Handshake ------");
  }
}
