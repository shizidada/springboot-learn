package org.moose.oauth.feign.fallback;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.moose.oauth.feign.OAuth2LoginFeignApi;
import org.moose.oauth.model.params.OAuthParam;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/22 12:07
 * @see org.moose.oauth.feign
 */
@Slf4j
@Component
public class OAuth2LoginFeignApiFallback implements OAuth2LoginFeignApi {

  public String getOAuthToken(Map<String, String> parameters) {
    log.warn("OAuth2LoginFeignApiFallback #getOAuthToken Fail");
    return null;
  }
}
