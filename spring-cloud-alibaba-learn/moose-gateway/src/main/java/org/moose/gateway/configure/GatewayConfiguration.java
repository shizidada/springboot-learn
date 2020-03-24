package org.moose.gateway.configure;

import org.moose.gateway.filter.CorsWebFilter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;

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
}
