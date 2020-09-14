package org.moose.operator.sender.impl;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.sender.SmsCodeSender;
import org.moose.operator.sender.model.ValidateCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
@Slf4j
@Component
public class DefaultSmsCodeSender implements SmsCodeSender {

  @Resource private RedisTemplate<String, Object> redisTemplate;

  @Override public void send(String mobile, String smsCode) {

    String smsMobileKey = RedisKeyConstants.SMS_MOBILE_KEY + mobile;

    // 计算发送次数
    Integer sendCount = (Integer) redisTemplate.opsForValue().get(smsMobileKey);
    if (sendCount == null) {
      redisTemplate.opsForValue()
          .set(smsMobileKey, 1, SecurityConstants.SMS_TIME_OF_DAY, TimeUnit.SECONDS);
    } else {
      // 判断手机号时间范围内，累计发送次数
      if (sendCount >= SecurityConstants.MAX_COUNT_OF_DAY) {
        throw new BusinessException(ResultCode.SMS_CODE_COUNT);
      }
      redisTemplate.opsForValue().increment(smsMobileKey);
      redisTemplate.expire(smsMobileKey, SecurityConstants.SMS_TIME_OF_DAY, TimeUnit.SECONDS);
    }

    // 保存 smsCode
    ValidateCode validateCode = new ValidateCode(smsCode, SecurityConstants.SMS_TIME_OF_TIMEOUT);
    String smsCodeKey = RedisKeyConstants.SMS_CODE_KEY + mobile;
    redisTemplate.opsForValue()
        .set(smsCodeKey, validateCode, SecurityConstants.SMS_TIME_OF_TIMEOUT, TimeUnit.SECONDS);
    log.info("手机号 [{}] 发送短信验证码 [{}]", mobile, smsCode);
  }
}
