package org.moose.business.oauth.feign;

import java.util.Map;
import org.moose.business.oauth.configure.OAuth2RequestTokenConfiguration;
import org.moose.business.oauth.feign.fallback.OAuth2RequestTokenApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * OAuth2 登录 Api
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/14 20:30
 * @see org.moose.business.oauth.feign
 */

@FeignClient(
    value = "business-oauth2-service",
    configuration = OAuth2RequestTokenConfiguration.class,
    fallback = OAuth2RequestTokenApiFallback.class
)
public interface OAuth2RequestTokenApi {

  /**
   * 通过 Feign 登录获取授权码
   *
   * @param parameters 授权参数
   * @return 授权信息
   */
  @RequestMapping(method = RequestMethod.POST, value = "/oauth/token")
  Map<String, Object> getOAuthToken(Map<String, String> parameters);

  /**
   * 退出，删除 access_token
   *
   * @param accessToken 令牌
   * @return 是否删除令牌成功
   */
  @RequestMapping(method = RequestMethod.POST, value = "/oauth/token/delete")
  boolean deleteToken(@RequestParam("accessToken") String accessToken);
}
