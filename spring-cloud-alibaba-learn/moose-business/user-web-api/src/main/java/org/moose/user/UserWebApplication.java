package org.moose.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
@EnableDubbo
public class UserWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserWebApplication.class, args);
  }
}
