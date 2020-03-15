package org.moose.oauth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * TODO: mock
 *
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

@FeignClient(value = "business-oauth2-service")
public interface OAuth2LoginFeignApi {

  /**
   * TODO: mock
   *
   * 授权登录
   *
   * @return 登录信息
   */
  @GetMapping("/user/login")
  String login();
}
