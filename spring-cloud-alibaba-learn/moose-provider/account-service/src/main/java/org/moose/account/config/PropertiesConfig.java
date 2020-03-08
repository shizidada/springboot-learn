package org.moose.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 21:31
 * @see org.moose.account.config
 */

@Configuration
public class PropertiesConfig {

  @Bean
  public GithubAuthorizeProperties githubAuthorizeProperties() {
    return new GithubAuthorizeProperties();
  }
}
