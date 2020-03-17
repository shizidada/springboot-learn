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
 * @date 2020 2020/3/13 20:20
 * @see org.moose.oauth
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class BusinessOAuth2Application {

  public static void main(String[] args) {
    SpringApplication.run(BusinessOAuth2Application.class, args);
  }
}
