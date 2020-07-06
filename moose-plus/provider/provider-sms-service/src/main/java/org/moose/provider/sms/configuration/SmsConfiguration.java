package org.moose.provider.sms.configuration;

import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 16:38:16:38
 * @see org.moose.provider.sms.configuration
 */
@Configuration
public class SmsConfiguration {

  @Bean
  SnowflakeIdWorker snowflakeIdWorker() {
    return new SnowflakeIdWorker(3, 6);
  }
}
