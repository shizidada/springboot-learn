package org.moose.business.user.service;

import org.moose.business.user.model.params.RegisterParam;
import org.moose.business.user.model.params.SmsCodeParam;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.business.user.model.params.LoginParam;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-24 13:31:13:31
 * @see org.moose.business.user.service
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

  /**
   * 根据手机号码查询账号
   *
   * @param phone 手机号码
   * @return 查询结果
   */
  boolean findByPhone(String phone);

  /**
   * 发送短信验证码
   *
   * @param smsCodeParam 发送短信
   * @return 查询结果
   */
  ResponseResult<?> sendSmsCode(SmsCodeParam smsCodeParam);
}
