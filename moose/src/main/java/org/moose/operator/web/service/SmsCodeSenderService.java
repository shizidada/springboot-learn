package org.moose.operator.web.service;

import org.moose.operator.model.dto.SmsCodeDTO;
import org.moose.operator.model.params.SmsCodeParam;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-15 23:23:23:23
 * @see org.moose.operator.web.service
 */
public interface SmsCodeSenderService {
  /**
   * 发送短信
   *
   * @param smsCodeParam 短信
   */
  void sendSmsCode(SmsCodeParam smsCodeParam);

  /**
   * 设置短信验证码过期
   *
   * @param phone 手机号码
   */
  void setSmsCodeCacheExpire(String phone);

  /**
   * 获取短信
   *
   * @param phone 手机号码
   * @return SmsCodeDTO
   */
  SmsCodeDTO getSmsCodeFromCacheByPhone(String phone);
}
