package org.moose.business.user.service;

import org.moose.commons.base.dto.ResponseResult;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:08
 * @see org.moose.business.user.service
 */
public interface LogoutService {

  /**
   * 注销退出账号
   *
   * @param accessToken 授权令牌
   * @return 是否注册成功
   */
  ResponseResult<?> logout(String accessToken);
}
