package org.moose.gateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-14 16:43:16:43
 * @see org.moose.gateway.filter
 */
public class CorsWebFilter implements WebFilter {
  private static final String ALL = "*";
  private static final String MAX_AGE = "3600L";

  /**
   * @param ctx   ServerWebExchange
   * @param chain WebFilterChain
   * @return Mono<Void>
   */
  @Override public Mono<Void> filter(ServerWebExchange ctx, WebFilterChain chain) {
    ServerHttpRequest request = ctx.getRequest();
    if (!CorsUtils.isCorsRequest(request)) {
      return chain.filter(ctx);
    }
    HttpHeaders requestHeaders = request.getHeaders();
    ServerHttpResponse response = ctx.getResponse();
    HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
    HttpHeaders headers = response.getHeaders();
    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
    headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
        requestHeaders.getAccessControlRequestHeaders());
    if (requestMethod != null) {
      headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
    }
    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
    headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
    if (request.getMethod() == HttpMethod.OPTIONS) {
      response.setStatusCode(HttpStatus.OK);
      return Mono.empty();
    }
    return chain.filter(ctx);
  }
}
