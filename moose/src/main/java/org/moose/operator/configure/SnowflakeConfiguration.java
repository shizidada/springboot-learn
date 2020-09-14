package org.moose.operator.configure;

import org.moose.operator.component.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author taohua
 */
@Configuration
public class SnowflakeConfiguration {

  @Bean
  public SnowflakeIdWorker snowflakeIdWorker() {
    return new SnowflakeIdWorker(0, 0);
  }
}
