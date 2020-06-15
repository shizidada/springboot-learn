package org.excel.operator.web.security.sms.sender;

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
public interface SmsCodeSender {
  void send(String mobile,String code);
}
