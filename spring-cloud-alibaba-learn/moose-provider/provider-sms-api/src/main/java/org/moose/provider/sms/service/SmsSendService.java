package org.moose.provider.sms.service;

/**
 *
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
   * 发送短信
   *
   * @param jsonStr 短信消息
   * @return 是否发送成功
   */
  public void add(String jsonStr);
}
