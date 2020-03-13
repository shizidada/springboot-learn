package org.moose.user;

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
 * @date 2020 2020/3/8 14:10
 * @see org.moose.user
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserWebApplication.class, args);
  }
}
