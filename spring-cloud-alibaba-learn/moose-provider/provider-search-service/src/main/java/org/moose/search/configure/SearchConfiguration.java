package org.moose.search.configure;

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
 * @date 2020 2020/4/9 23:29
 * @see org.moose.search.configure
 */
@Configuration
public class SearchConfiguration {

  @Bean
  public SnowflakeIdWorker snowflakeIdWorker() {
    return new SnowflakeIdWorker(12, 18);
  }
}
