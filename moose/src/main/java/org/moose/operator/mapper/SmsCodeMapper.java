package org.moose.operator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.moose.operator.model.domain.SmsCodeDO;

/**
 * @author toahua
 */
@Mapper
public interface SmsCodeMapper {
  /**
   * insert sms code
   *
   * @param smsCodeDO sms code
   */
  void insertSmsCode(SmsCodeDO smsCodeDO);
}
