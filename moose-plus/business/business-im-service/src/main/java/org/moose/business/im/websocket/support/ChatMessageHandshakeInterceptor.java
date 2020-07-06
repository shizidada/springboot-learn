package org.moose.business.im.websocket.support;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @date 2020-04-08 14:31:14:31
 * @see org.moose.business.im.websocket.support
 */
@Component
@Slf4j
public class ChatMessageHandshakeInterceptor implements HandshakeInterceptor {

  /**
   * beforeHandshake
   *
   * @param serverHttpRequest
   * @param serverHttpResponse
   * @param webSocketHandler
   * @param map
   * @return
   * @throws Exception
   */
  @Override
  public boolean beforeHandshake(
      ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
      WebSocketHandler webSocketHandler, Map<String, Object> map
  ) throws Exception {
    // TODO: 通过 oauth2 是否还需要手动去校验 token 有效
    log.info("ChatMessageHandshakeInterceptor #开始握手！ ");
    // 处理请求路径为 ---> /ws/{uid}

    // TODO: 是否可以通过 Authentication 去拿 用户信息
    // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String path = serverHttpRequest.getURI().getPath();
    String[] split = StringUtils.split(path, "/");
    // 请求路径切割后不等于2的排除掉
    if (split.length != 2) {
      return false;
    }
    // 第二个参数不是数字排除掉
    String accountId = split[1];
    if (StringUtils.isEmpty(accountId)) {
      return false;
    }
    map.put("accountId", accountId);
    return true;
  }

  /**
   * afterHandshake
   *
   * @param serverHttpRequest
   * @param serverHttpResponse
   * @param webSocketHandler
   * @param e
   */
  @Override
  public void afterHandshake(ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    log.info("ChatMessageHandshakeInterceptor #握手完成！ ");
  }
}
