package org.moose.operator.web.service.impl;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.mapper.SmsCodeMapper;
import org.moose.operator.model.domain.SmsCodeDO;
import org.moose.operator.model.dto.SmsCodeDTO;
import org.moose.operator.model.params.SmsCodeParam;
import org.moose.operator.web.service.AccountService;
import org.moose.operator.web.service.SmsCodeSenderService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

  @Resource
  private SmsCodeMapper smsCodeMapper;

  @Resource
  private AccountService accountService;

  @Resource
  private UserInfoService userInfoService;

  @Resource private RedisTemplate<String, Object> redisTemplate;

  //String type = smsCodeParam.getType();
  //if (StringUtils.equals(type, SmsTypes.RESET_PHONE)) {
  //  String phone = smsCodeParam.getPhone();
  //  AccountDTO accountDTO = accountService.getAccountInfo();
  //  UserInfoDTO userInfoDTO = userInfoService.getUserInfoByAccountId(accountDTO.getAccountId());
  //  if (StringUtils.equals(phone, userInfoDTO.getPhone())) {
  //    throw new BusinessException(ResultCode.PHONE_EXITS_WITH_CURRENT);
  //  }
  //}

  @Transactional(rollbackFor = Exception.class)
  @Override public void sendSmsCode(SmsCodeParam smsCodeParam) {

    // 发送短信
    sendAndSaveSmsCode(smsCodeParam);
  }

  private void sendAndSaveSmsCode(SmsCodeParam smsCodeParam) {
    // 生成短信验证码
    String smsCode = RandomStringUtils.randomNumeric(6);

    String phoneNumber = smsCodeParam.getPhone();

    String smsType = smsCodeParam.getType();

    // reset save sms code to redis
    String smsCodeKey = String.format(RedisKeyConstants.SMS_CODE_KEY, smsType, phoneNumber);

    // TODO: get sms code not expired return ?
    SmsCodeDTO smsCodeDTO = (SmsCodeDTO) redisTemplate.opsForValue().get(smsCodeKey);
    if (ObjectUtils.isNotEmpty(smsCodeDTO) && !smsCodeDTO.getExpired()) {
      return;
    }

    // sms code save db
    SmsCodeDO smsCodeDO = new SmsCodeDO();
    smsCodeDO.setPhone(phoneNumber);
    smsCodeDO.setType(smsType);
    smsCodeDO.setCode(smsCode);
    smsCodeMapper.insertSmsCode(smsCodeDO);

    smsCodeDTO =
        new SmsCodeDTO(smsCode, smsType, SecurityConstants.SMS_TIME_OF_TIMEOUT);
    redisTemplate.opsForValue()
        .set(smsCodeKey, smsCodeDTO, SecurityConstants.SMS_TIME_OF_TIMEOUT, TimeUnit.SECONDS);
    log.info("phone number [{}] send sms code [{}]", phoneNumber, smsCode);
  }
}
