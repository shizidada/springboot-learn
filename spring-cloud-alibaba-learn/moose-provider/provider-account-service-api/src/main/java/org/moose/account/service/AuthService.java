package org.moose.account.service;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/8 14:26
 * @see org.moose.user.service
 */
public interface AuthService {
  /**
   * 获取登录
   *
   * @param type 请三方类型
   * @return 请三方 url
   */
  String getAuthUrl(String type);
}
