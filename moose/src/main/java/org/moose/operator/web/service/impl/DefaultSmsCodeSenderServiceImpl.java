package org.moose.operator.web.service.impl;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.model.dto.SmsCodeDTO;
import org.moose.operator.model.params.SmsCodeParam;
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

  @Override public ResponseResult<Object> sendSmsCode(SmsCodeParam smsCodeParam) {

    // 发送短信
    sendAndSaveSmsCode(smsCodeParam);

    return new ResponseResult<>(true, "短信验证码发送成功");
  }

  private void sendAndSaveSmsCode(SmsCodeParam smsCodeParam) {
    // 生成短信验证码
    String smsCode = RandomStringUtils.randomNumeric(6);

    String phoneNumber = smsCodeParam.getPhone();

    // 计算发送次数
    String smsSendCountKey = RedisKeyConstants.SMS_PHONE_KEY + phoneNumber;
    Integer sendCount = (Integer) redisTemplate.opsForValue().get(smsSendCountKey);
    if (ObjectUtils.isEmpty(sendCount)) {
      redisTemplate.opsForValue()
          .set(smsSendCountKey, 1, SecurityConstants.SMS_TIME_OF_DAY, TimeUnit.SECONDS);
    } else {
      // 判断手机号时间范围内，累计发送次数
      if (sendCount >= SecurityConstants.MAX_COUNT_OF_DAY) {
        throw new BusinessException(ResultCode.SMS_CODE_COUNT);
      }
      redisTemplate.opsForValue().increment(smsSendCountKey);
      redisTemplate.expire(smsSendCountKey, SecurityConstants.SMS_TIME_OF_DAY, TimeUnit.SECONDS);
    }

    String smsType = smsCodeParam.getType();

    // reset save sms code to redis
    String smsCodeKey =
        String.format(RedisKeyConstants.SMS_CODE_KEY, smsType, phoneNumber);

    // TODO: get sms code not expired return ?
    SmsCodeDTO smsCodeDTO = (SmsCodeDTO) redisTemplate.opsForValue().get(smsCodeKey);
    if (ObjectUtils.isNotEmpty(smsCodeDTO) && !smsCodeDTO.getExpired()) {
      return;
    }

    smsCodeDTO =
        new SmsCodeDTO(smsCode, smsType, SecurityConstants.SMS_TIME_OF_TIMEOUT);
    redisTemplate.opsForValue()
        .set(smsCodeKey, smsCodeDTO, SecurityConstants.SMS_TIME_OF_TIMEOUT, TimeUnit.SECONDS);
    log.info("phone number [{}] send sms code [{}]", phoneNumber, smsCode);
  }
}
