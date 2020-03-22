package org.moose.oauth.feign;

import feign.Headers;
import feign.RequestLine;
import java.util.Map;
import org.moose.oauth.configure.FeignRequestConfiguration;
import org.moose.oauth.feign.fallback.OAuth2LoginFeignApiFallback;
import org.moose.oauth.model.params.OAuthParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * OAuth2 登录 Api
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/14 20:30
 * @see org.moose.oauth.feign
 */

@FeignClient(
    value = "business-oauth2-service",
    configuration = FeignRequestConfiguration.class,
    fallback = OAuth2LoginFeignApiFallback.class
)
public interface OAuth2LoginFeignApi {

  /**
   * 通过 Feign 登录获取授权码
   *
   * @param parameters 授权参数
   * @return 授权信息
   */
  //@RequestLine("POST /oauth/token")
  //@Headers({"Content-Type: multipart/form-data"})
  //@Headers({"Content-Type: application/x-www-form-urlencoded"})
  @RequestMapping(method = RequestMethod.POST, value = "/oauth/token")
  String getOAuthToken(Map<String, String> parameters);
}
