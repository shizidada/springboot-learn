package org.moose.user.service;

import org.moose.commons.base.dto.ResponseResult;
import org.moose.user.model.params.LoginParam;
import org.moose.user.model.params.RegisterParam;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-24 13:31:13:31
 * @see org.moose.user.service
 */
public interface UserService {

  /**
   * 登录
   *
   * @param loginParam 登录信息
   * @return 是否登录成功
   */
  ResponseResult<?> login(LoginParam loginParam);

  /**
   * 注册
   *
   * @param registerParam 注册信息
   * @return 是否注册成功
   */
  ResponseResult<?> register(RegisterParam registerParam);

  /**
   * 注销退出账号
   *
   * @param accessToken 授权令牌
   * @return 是否注册成功
   */
  ResponseResult<?> logout(String accessToken);
}
