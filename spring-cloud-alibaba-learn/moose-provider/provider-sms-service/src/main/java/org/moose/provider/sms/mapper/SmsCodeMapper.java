package org.moose.provider.sms.mapper;

import org.moose.provider.sms.model.domain.SmsCodeDO;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 16:33:16:33
 * @see org.moose.provider.sms.mapper
 */
public interface SmsCodeMapper {

  /**
   * 短信持久化
   *
   * @param smsCodeDO 短信信息
   *
   * @return 是否插入成功
   */
  public int insert(SmsCodeDO smsCodeDO);
}
