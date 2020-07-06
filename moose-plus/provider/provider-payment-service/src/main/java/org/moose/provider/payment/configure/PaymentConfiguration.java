package org.moose.provider.payment.configure;

import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 12:04
 * @see org.moose.provider.payment.configure
 */

@Configuration
public class PaymentConfiguration {

  @Bean
  public SnowflakeIdWorker snowflakeIdWorker() {
    return new SnowflakeIdWorker(15, 30);
  }
}
