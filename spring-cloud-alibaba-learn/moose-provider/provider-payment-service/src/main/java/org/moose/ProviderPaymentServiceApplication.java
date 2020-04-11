package org.moose;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 10:14
 * @see org.moose
 */
@SpringBootApplication
@EnableDubbo
@EnableTransactionManagement
public class ProviderPaymentServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(ProviderPaymentServiceApplication.class, args);
  }
}
