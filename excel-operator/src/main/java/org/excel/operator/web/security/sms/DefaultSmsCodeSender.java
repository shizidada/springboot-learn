package org.excel.operator.web.security.sms;

import org.excel.operator.web.security.sms.sender.SmsCodeSender;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-15 23:23:23:23
 * @see org.excel.operator.web.security.sms
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
  @Override public void send(String mobile, String code) {
    System.out.println("向手机" + mobile + "发送短信验证码" + code + "");
  }
}
