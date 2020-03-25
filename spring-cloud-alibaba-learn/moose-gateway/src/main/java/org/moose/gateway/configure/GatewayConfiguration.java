package org.moose.gateway.configure;

import java.util.Collections;
import java.util.List;
import org.moose.gateway.filter.CorsWebFilter;
import org.moose.gateway.handle.JsonExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

/**
 * 网关配置
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-14 16:48:16:48
 * @see org.moose.gateway.configure
 */
@Configuration
public class GatewayConfiguration {

  @Bean
  public RouteDefinitionLocator discoveryClientRouteDefinitionLocator(
      DiscoveryClient discoveryClient, DiscoveryLocatorProperties properties) {
    return new DiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
  }

  @Bean
  public ServerCodecConfigurer serverCodecConfigurer() {
    return new DefaultServerCodecConfigurer();
  }

  @Bean
  public CorsWebFilter corsWebFilter() {
    return new CorsWebFilter();
  }

  @Primary
  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public ErrorWebExceptionHandler errorWebExceptionHandler(
      ObjectProvider<List<ViewResolver>> viewResolversProvider,
      ServerCodecConfigurer serverCodecConfigurer) {
    JsonExceptionHandler jsonExceptionHandler = new JsonExceptionHandler();
    jsonExceptionHandler.setViewResolvers(
        viewResolversProvider.getIfAvailable(Collections::emptyList));
    jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
    jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
    return jsonExceptionHandler;
  }
}
