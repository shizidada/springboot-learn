package org.excel.operator.web.security.sms;

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

  @Resource RedisTemplate<String, Object> redisTemplate;

  @Override public void send(String mobile, String code) {
    ValidateCode validateCode = new ValidateCode(code, 30);
    redisTemplate.opsForValue().set(SecurityConstants.SMS_KEY + mobile, validateCode);
    log.info("向手机" + mobile + "发送短信验证码" + code);
  }
}
