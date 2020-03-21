package org.moose.oauth.feign;

import org.springframework.cloud.openfeign.FeignClient;

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

@FeignClient(value = "business-oauth2-service")
public interface OAuth2LoginFeignApi {
}
