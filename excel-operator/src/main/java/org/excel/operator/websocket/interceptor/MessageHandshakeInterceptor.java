package org.excel.operator.websocket.interceptor;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    log.info("----------开始握手！------------");
    // 处理请求路径为 ---> /ws/{uid}
    String path = serverHttpRequest.getURI().getPath();
    String[] split = StringUtils.split(path, "/");
    // 请求路径切割后不等于2的排除掉
    if (split.length != 2) {
      return false;
    }
    // 第二个参数不是数字排除掉
    String userId = split[1];
    if (StringUtils.isEmpty(userId)) {
      return false;
    }
    map.put("uid", userId);
    return true;
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
    log.info("----------握手完成！------------");
  }
}
