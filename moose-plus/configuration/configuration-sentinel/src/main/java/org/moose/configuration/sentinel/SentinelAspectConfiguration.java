package org.moose.configuration.sentinel;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
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
 * @date 2020-04-08 12:55:12:55
 * @see org.moose.configuration.sentinel
 */
@Configuration
public class SentinelAspectConfiguration {

  @Bean
  public SentinelResourceAspect sentinelResourceAspect() {
    return new SentinelResourceAspect();
  }
}
