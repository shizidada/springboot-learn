package org.excel.operator.configure;

import org.excel.operator.component.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author taohua
 */
@Configuration
public class SnowflakeConfig {

  @Bean
  public SnowflakeIdWorker snowflakeIdWorker() {
    return new SnowflakeIdWorker(0, 0);
  }
}
