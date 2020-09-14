package org.moose.operator.web.service;

import org.moose.operator.common.api.ResponseResult;

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
   * @param mobile 手机号
   * @return ResponseResult<Object>
   */
  ResponseResult<Object> sendSmsCode(String mobile);
}
