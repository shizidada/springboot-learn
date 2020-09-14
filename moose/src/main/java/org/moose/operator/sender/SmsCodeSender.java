package org.moose.operator.sender;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-15 23:23:23:23
 * @see org.moose.operator.web.security.sms
 */
public interface SmsCodeSender {
  /**
   * 发送短信
   *
   * @param mobile 手机号
   * @param code   短信验证码
   */
  void send(String mobile, String code);
}
