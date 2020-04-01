package org.moose.provider.sms.model.domain;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moose.commons.base.entity.BaseDO;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 14:09:14:09
 * @see org.moose.provider.sms.model.domain
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SmsCodeDO extends BaseDO {

  /**
   * 主键 id
   */
  private Long smsId;

  /**
   * 手机号码
   */
  private String phone;

  /**
   * 验证码
   */
  private String verifyCode;

  /**
   * 发送短信类型
   *
   * 0 -> 注册
   */
  private String type;

  /**
   * 过期时间
   */
  private LocalDateTime expiredTime;
}
