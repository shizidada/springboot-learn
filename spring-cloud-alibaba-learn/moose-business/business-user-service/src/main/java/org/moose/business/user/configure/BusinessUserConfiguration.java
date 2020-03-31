package org.moose.business.user.configure;

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
 * @date 2020-03-31 13:31:13:31
 * @see org.moose.business.user.configure
 */
@Configuration
public class BusinessUserConfiguration {

  @Bean
  SnowflakeIdWorker snowflakeIdWorker() {
    return new SnowflakeIdWorker(1, 1);
  }
}
