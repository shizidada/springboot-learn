package org.moose;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/8 10:27
 * @see org.moose.provider.user
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class ProviderUserServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(ProviderUserServiceApplication.class, args);
  }
}
