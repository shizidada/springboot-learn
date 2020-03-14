package org.moose.account.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.moose.account.service.AuthService;
import org.apache.dubbo.config.annotation.Service;
import org.moose.account.config.GithubAuthorizeProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/8 14:46
 * @see org.moose.account.service.impl
 */

@Service(version = "1.0.0")
@Slf4j
public class AuthServiceImpl implements AuthService {

  @Autowired
  private GithubAuthorizeProperties githubAuthorizeProperties;

  @Override
  public String getAuthUrl(String type) {
    String url = "";
    switch (type) {
      case "github":
        String json = JSON.toJSONString(githubAuthorizeProperties);
        String clientId = githubAuthorizeProperties.getClientId();
        String redirectUri = githubAuthorizeProperties.getRedirectUri();
        String scope = githubAuthorizeProperties.getScope();
        String state = githubAuthorizeProperties.getState();
        url = "https://github.com/login/oauth/authorize?client_id="
            + clientId + "&redirect_uri=" + redirectUri + "&scope=" + scope + "&state=" + state;
        log.info(url, json);
        break;
      default:
        break;
    }
    return url;
  }
}
