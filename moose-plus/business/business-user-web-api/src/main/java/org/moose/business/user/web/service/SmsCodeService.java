package org.moose.business.user.web.service;

import org.moose.business.user.web.model.params.SmsCodeParam;
import org.moose.commons.base.dto.ResponseResult;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:14
 * @see org.moose.business.user.web.service
 */
public interface SmsCodeService {

  /**
   * 发送短信验证码
   *
   * @param smsCodeParam 发送短信
   * @return 发送结果
   */
  ResponseResult<?> sendSmsCode(SmsCodeParam smsCodeParam);
}
