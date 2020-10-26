package org.moose.operator.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author taohua
 */
@Slf4j
public class CheckHeaderTokenFilter extends OncePerRequestFilter {
  private final  String AUTHORIZATION_PARAM = "Authorization";

  private TokenStore tokenStore;

  private List<String> anonymousUrlList;

  public CheckHeaderTokenFilter(TokenStore tokenStore,
      List<String> anonymousUrlList) {
    this.tokenStore = tokenStore;
    this.anonymousUrlList = anonymousUrlList;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String accessToken = this.extractToken(request);
    String url = request.getRequestURI();
    // check access_token
    if (StringUtils.isNotEmpty(accessToken)) {
      if (anonymousUrlList.contains(url)) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        if (ObjectUtils.isNotEmpty(oAuth2AccessToken)) {
          filterChain.doFilter(request, response);
          return;
        }

        if (ObjectUtils.isEmpty(oAuth2AccessToken)) {
          CheckHeaderTokenRequestWrapper requestWrapper = new CheckHeaderTokenRequestWrapper(request);
          requestWrapper.addHeader(AUTHORIZATION_PARAM, "");
          filterChain.doFilter(requestWrapper, response);
          return;
        }
      }
    }
    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    // first check the header...
    String token = extractHeaderToken(request);

    if (token == null) {
      logger.debug("Token not found in headers. Trying request parameters.");
      token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
      if (token == null) {
        logger.debug("Token not found in request parameters.  Not an OAuth2 request.");
      } else {
        request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
            OAuth2AccessToken.BEARER_TYPE);
      }
    }
    return token;
  }

  private String extractHeaderToken(HttpServletRequest request) {
    Enumeration<String> headers = request.getHeaders(AUTHORIZATION_PARAM);
    // typically there is only one (most servers enforce that)
    while (headers.hasMoreElements()) {
      String value = headers.nextElement();
      if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
        String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
        // Add this here for the auth details later. Would be better to change the signature of this method.
        request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
            value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
        int commaIndex = authHeaderValue.indexOf(',');
        if (commaIndex > 0) {
          authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
      }
    }
    return null;
  }
}
