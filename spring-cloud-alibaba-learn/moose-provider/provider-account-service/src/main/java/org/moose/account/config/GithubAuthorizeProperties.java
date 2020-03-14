package org.moose.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/7 20:57
 * @see org.moose.account.config
 */
@Data
@ConfigurationProperties(prefix = "github.authorize")
public class GithubAuthorizeProperties {

  private String clientId;

  private String redirectUri;

  private String login;

  private String scope;

  private String state;

  private String allowSignup;
}
