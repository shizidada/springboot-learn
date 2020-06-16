package org.excel.operator.web.security.sms;

import java.time.Duration;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.constants.SecurityConstants;
import org.excel.operator.web.security.sms.sender.SmsCodeSender;
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
 * @see org.excel.operator.web.security.sms
 */
@Slf4j
@Component
public class DefaultSmsCodeSender implements SmsCodeSender {
  /**
   * 15 分钟
   */
  private static final Integer SMS_KEY_TIMEOUT = 15;

  @Resource private RedisTemplate<String, Object> redisTemplate;

  @Override public void send(String mobile, String smsCode) {

    /**
     * TODO: 判断手机号时间范围内，累计发送次数 default 6 ??
     */
    ValidateCode validateCode = new ValidateCode(smsCode, SMS_KEY_TIMEOUT);
    redisTemplate.opsForValue()
        .set(SecurityConstants.SMS_KEY + mobile, validateCode, Duration.ofMinutes(SMS_KEY_TIMEOUT));
    log.info("手机号 [{}] 发送短信验证码 [{}]", mobile, smsCode);
  }
}
