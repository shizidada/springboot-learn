package org.moose.operator.web.service.impl;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.model.dto.ValidateCodeDTO;
import org.moose.operator.web.service.SmsCodeSenderService;
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
 * @see org.moose.operator.web.service.impl
 */
@Slf4j
@Component
public class DefaultSmsCodeSenderServiceImpl implements SmsCodeSenderService {

  @Resource private RedisTemplate<String, Object> redisTemplate;

  @Override public ResponseResult<Object> sendSmsCode(String mobile) {
    if (StringUtils.isBlank(mobile)) {
      throw new BusinessException(ResultCode.PHONE_NUMBER_IS_EMPTY);
    }

    // TODO： 判断手机号码是否存在

    // 发送短信验证码并保存到 redis 中
    // TODO: try {} catch() {}
    sendAndSaveSmsCode(mobile);

    return new ResponseResult<>(true, "短信验证码发送成功");
  }

  private void sendAndSaveSmsCode(String mobile) {
    // 生成短信验证码
    String smsCode = RandomStringUtils.randomNumeric(6);

    String smsMobileKey = RedisKeyConstants.SMS_MOBILE_KEY + mobile;

    // 保存 smsCode
    ValidateCodeDTO validateCode =
        new ValidateCodeDTO(smsCode, SecurityConstants.SMS_TIME_OF_TIMEOUT);
    String smsCodeKey = RedisKeyConstants.SMS_CODE_KEY + mobile;
    redisTemplate.opsForValue()
        .set(smsCodeKey, validateCode, SecurityConstants.SMS_TIME_OF_TIMEOUT, TimeUnit.SECONDS);
    log.info("手机号 [{}] 发送短信验证码 [{}]", mobile, smsCode);

    // 计算发送次数
    Integer sendCount = (Integer) redisTemplate.opsForValue().get(smsMobileKey);
    if (ObjectUtils.isEmpty(sendCount)) {
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
  }
}
