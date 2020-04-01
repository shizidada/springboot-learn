package org.moose.provider.sms;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 14:22:14:22
 * @see org.moose.provider.sms
 */
@SpringBootApplication
@EnableDubbo
public class ProviderSmsServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(ProviderSmsServiceApplication.class, args);
  }
}
