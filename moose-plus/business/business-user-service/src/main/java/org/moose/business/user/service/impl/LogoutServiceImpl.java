package org.moose.business.user.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.business.oauth.feign.OAuth2RequestTokenApi;
import org.moose.business.user.service.LogoutService;
import org.moose.commons.base.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:08
 * @see org.moose.business.user.service.impl
 */
@Service
@Slf4j
public class LogoutServiceImpl implements LogoutService {

  @Resource
  private OAuth2RequestTokenApi oAuth2RequestTokenApi;

  @Override public ResponseResult<?> logout(String accessToken) {
    boolean result = oAuth2RequestTokenApi.deleteToken(accessToken);
    return new ResponseResult<>(result);
  }
}
