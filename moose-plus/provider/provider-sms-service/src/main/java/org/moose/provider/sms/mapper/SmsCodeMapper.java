package org.moose.provider.sms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.moose.provider.sms.model.domain.SmsCodeDO;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 16:33:16:33
 * @see org.moose.provider.sms.mapper
 */
@Mapper
public interface SmsCodeMapper {

  /**
   * 短信持久化
   *
   * @param smsCodeDO 短信信息
   * @return 是否插入成功
   */
  int insert(SmsCodeDO smsCodeDO);

  /**
   * 根据手机、验证码、SmsToken 是否过期
   *
   * 查询短信校验码
   *
   * @param smsCodeDO 短信信息
   * @return 是否存在
   */
  SmsCodeDO findSmsCodePhoneVerifyCodeSmsTokenNotExpired(SmsCodeDO smsCodeDO);
}
