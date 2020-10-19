package org.moose.operator.web.service;

import javax.servlet.http.HttpServletRequest;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.params.AuthTokenParam;
import org.moose.operator.model.params.LoginInfoParam;
import org.moose.operator.model.params.RegisterInfoParam;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.moose.operator.web.service
 */
public interface AccountService {

  /**
   * 注册
   *
   * @param registerInfoParam 注册信息
   * @param request           #HttpServletRequest
   * @return 是否注册成功
   */
  Boolean saveAccount(HttpServletRequest request,
      RegisterInfoParam registerInfoParam);

  /**
   * 登录 获取 Token
   *
   * @param loginInfoParam 登录信息
   * @return 是否登录成功信息
   */
  String getToken(LoginInfoParam loginInfoParam);

  /**
   * 退出登录 删除 Token
   *
   * @param accessToken accessToken
   * @return 是否退出成功信息
   */
  Boolean removeToken(String accessToken);

  /**
   * 根据账号查找用户
   *
   * @param accountName 账号
   * @return AccountDTO
   */
  AccountDTO getByAccountName(String accountName);

  /**
   * 根据手机号码查找用户
   *
   * @param phone 账号
   * @return AccountDTO
   */
  AccountDTO getAccountByPhone(String phone);

  /**
   * 获取用户信息
   *
   * @return AccountDTO
   */
  AccountDTO getAccountInfo();

  /**
   * 判断账号是否登陆
   *
   * @return 是否登陆
   */
  boolean isLogin();

  /**
   * 获取授权信息
   *
   * @return 授权信息
   */
  Object getPrincipal();

  /**
   * 刷新 token
   *
   * @param authTokenParam authTokenParam
   * @return refreshToken
   */
  String getRefreshTokenByAccessToken(AuthTokenParam authTokenParam);

  /**
   * 刷新 token
   *
   * @param authTokenParam authTokenParam
   * @return accessToken
   */
  String getAccessTokenByRefreshToken(AuthTokenParam authTokenParam);

  /**
   * 更新账号手机号码
   *
   * @param accountId 账号Id
   * @param phone     手机号码
   * @return 是否更新成功
   */
  boolean updateAccountPhone(String accountId, String phone);
}
