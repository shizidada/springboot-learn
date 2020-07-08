package org.moose.business.oauth.configure;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-30 15:50:15:50
 * @see org.moose.business.oauth.configure
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends
    SavedRequestAwareAuthenticationSuccessHandler {

  @Resource
  private ObjectMapper objectMapper;

  @Resource
  private ClientDetailsService clientDetailsService;

  @Resource
  private AuthorizationServerTokenServices authorizationServerTokenServices;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    log.info("用户登录成功 [{}]", objectMapper.writeValueAsString(authentication));

    String header = request.getHeader("Authorization");
    if (header == null || !header.toLowerCase().startsWith("basic ")) {
      throw new UnapprovedClientAuthenticationException("请求头中没有clientId");
    }

    String[] tokens = extractAndDecodeHeader(header, request);
    assert tokens.length == 2;

    String clientId = tokens[0];
    String clientSecret = tokens[1];
    ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
    if (clientDetails == null) {
      throw new UnapprovedClientAuthenticationException("clientId配置信息不存在,clientId=" + clientId);
    } else if (!new BCryptPasswordEncoder().matches(clientSecret,
        clientDetails.getClientSecret())) {
      throw new UnapprovedClientAuthenticationException("clientSecret不匹配,clientId=" + clientId);
    }

    // grantType 为自定义的"custom"
    TokenRequest tokenRequest =
        new TokenRequest(new HashMap<>(16), clientId, clientDetails.getScope(), "custom");
    OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
    OAuth2Authentication oAuth2Authentication =
        new OAuth2Authentication(oAuth2Request, authentication);
    OAuth2AccessToken accessToken =
        authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(accessToken));
  }

  /**
   * Decodes the header into a username and password.
   *
   * @throws BadCredentialsException if the Basic header is not present or is not valid Base64
   */
  private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
      throws IOException {

    byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
    byte[] decoded;
    try {
      decoded = Base64.getDecoder().decode(base64Token);
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException(
          "Failed to decode basic authentication token");
    }

    String token = new String(decoded, StandardCharsets.UTF_8);

    int delim = token.indexOf(":");

    if (delim == -1) {
      throw new BadCredentialsException("Invalid basic authentication token");
    }
    return new String[] {token.substring(0, delim), token.substring(delim + 1)};
  }
}
