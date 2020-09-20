package org.moose.operator.web.service;

import javax.servlet.http.HttpServletRequest;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.params.LoginParam;
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
  ResponseResult<Object> saveAccount(HttpServletRequest request,
      RegisterInfoParam registerInfoParam);

  /**
   * 登录 获取 Token
   *
   * @param loginParam 登录信息
   * @return 是否登录成功信息
   */
  ResponseResult<Object> getToken(LoginParam loginParam);

  /**
   * 退出登录 删除 Token
   *
   * @param accessToken accessToken
   * @return 是否退出成功信息
   */
  ResponseResult<Object> removeToken(String accessToken);

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
   * @return ResponseResult<AccountDTO>
   */
  ResponseResult<Object> getAccountInfo();

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
