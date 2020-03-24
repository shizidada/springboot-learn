package org.moose.oauth.controller;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/21 10:41
 * @see org.moose.oauth.controller
 */
@RestController
@RequestMapping("/oauth/token")
@Slf4j
public class OAuth2Controller {

  @Resource
  public TokenStore tokenStore;

  @RequestMapping(method = RequestMethod.POST, value = "/delete")
  public boolean deleteToken(String token) {
    OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
    tokenStore.removeAccessToken(oAuth2AccessToken);
    return true;
  }
}
