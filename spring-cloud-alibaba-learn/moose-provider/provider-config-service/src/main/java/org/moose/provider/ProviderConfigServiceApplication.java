package org.moose.provider;

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
 * @date 2020 2020/4/7 21:19
 * @see org.moose.provider
 */
@SpringBootApplication
@EnableDubbo
public class ProviderConfigServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProviderConfigServiceApplication.class, args);
  }
}
