package org.moose.provider.sms.service;

import org.moose.provider.sms.model.dto.SmsCodeDTO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 14:05:14:05
 * @see org.moose.provider.user.service
 */
public interface SmsSendService {

  /**
   * 发送短信 by rocketMQ
   *
   * @param jsonStr 短信消息
   */
  void addSmsCode(String jsonStr);

  /**
   * 发送短信
   *
   * @param smsCodeDTO SmsCodeDTO
   * @return 是否发送成功
   */
  int addSmsCode(SmsCodeDTO smsCodeDTO);

  /**
   * 校验短信验证码是否正确
   *
   * @param smsCodeDTO 短信校验码
   */
  void checkSmsCode(SmsCodeDTO smsCodeDTO);
}
