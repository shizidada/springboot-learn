package org.moose.operator.web.service;

import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.model.params.LoginParam;

/**
 * @author taohua
 */
public interface LoginService {

  /**
   * 登录
   *
   * @param loginParam 登录信息
   * @return 是否登录成功信息
   */
  ResponseResult<Object> login(LoginParam loginParam);

  /**
   * 判断账号是否登陆
   *
   * @return 是否登陆
   */
  ResponseResult<Object> isLogin();

  /**
   * 获取授权信息
   *
   * @return 授权信息
   */
  Object getPrincipal();

  /**
   * 刷新 token
   *
   * @param accessToken accessToken
   * @return refreshToken
   */
  ResponseResult<Object> getRefreshTokenByAccessToken(String accessToken);

  /**
   * 刷新 token
   *
   * @param refreshToken refreshToken
   * @return accessToken
   */
  ResponseResult<Object> getAccessTokenByRefreshToken(String refreshToken);
}
