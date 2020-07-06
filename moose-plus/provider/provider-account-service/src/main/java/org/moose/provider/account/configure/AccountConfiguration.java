package org.moose.provider.account.configure;

import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 21:31
 * @see org.moose.provider.account.configure
 */

@Configuration
public class AccountConfiguration {

  @Bean
  public GithubAuthorizeProperties githubAuthorizeProperties() {
    return new GithubAuthorizeProperties();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SnowflakeIdWorker snowflakeIdWorker() {
    return new SnowflakeIdWorker(3, 8);
  }
}
