package org.excel.operator.web.security.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 21:57
 * @see org.excel.operator.component
 */
@Component
@Slf4j
public class CustomLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
    implements LogoutSuccessHandler {
  private static final String BEARER_AUTHENTICATION = "Bearer ";

  private static final String HEADER_AUTHORIZATION = "Authorization ";

  @Autowired
  private TokenStore tokenStore;

  @Override
  public void onLogoutSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication authentication) {
    String token = httpServletRequest.getHeader(HEADER_AUTHORIZATION);
    if (token != null && token.startsWith(BEARER_AUTHENTICATION)) {
      OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token.split(" ")[0]);
      if (oAuth2AccessToken != null) {
        tokenStore.removeAccessToken(oAuth2AccessToken);
      }
    }
  }
}