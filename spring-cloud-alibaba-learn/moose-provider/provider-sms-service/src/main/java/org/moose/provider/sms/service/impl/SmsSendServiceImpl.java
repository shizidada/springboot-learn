package org.moose.provider.sms.service.impl;

import com.alibaba.fastjson.JSON;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.moose.commons.provider.exception.ProviderRpcException;
import org.moose.provider.sms.mapper.SmsCodeMapper;
import org.moose.provider.sms.model.domain.SmsCodeDO;
import org.moose.provider.sms.model.dto.SmsCodeDTO;
import org.moose.provider.sms.service.SmsSendService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-04-01 14:20:14:20
 * @see org.moose.provider.sms.service.impl
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class SmsSendServiceImpl implements SmsSendService {

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Resource
  private SmsCodeMapper smsCodeMapper;

  @Override
  public void addSmsCode(String jsonStr) {
    if (jsonStr == null || StringUtils.isBlank(jsonStr)) {
      throw new ProviderRpcException(ResultCode.SMS_CODE_BODY_MUST_NOT_BE_NULL);
    }

    SmsCodeDTO smsCodeDTO = JSON.parseObject(jsonStr, SmsCodeDTO.class);
    if (smsCodeDTO == null) {
      throw new ProviderRpcException(ResultCode.SMS_CODE_BODY_PARSE_ERROR);
    }

    SmsCodeDO smsCodeDO = new SmsCodeDO();
    BeanUtils.copyProperties(smsCodeDTO, smsCodeDO);

    Long smsId = snowflakeIdWorker.nextId();
    smsCodeDO.setSmsId(smsId);

    String smsCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    smsCodeDO.setVerifyCode(smsCode);

    // 15 分钟后过期
    smsCodeDO.setExpiredTime(LocalDateTime.now().plusMinutes(15));
    smsCodeDO.setCreateTime(LocalDateTime.now());
    smsCodeDO.setUpdateTime(LocalDateTime.now());
    int result = smsCodeMapper.insert(smsCodeDO);
    if (result < 0) {
      log.info("保存手机验证码失败 :: {}", smsCode);
    }
    // 发送短信验证码
    log.info("发送短信验证码 :: {}", smsCode);
  }

  @Override
  public int addSmsCode(SmsCodeDTO smsCodeDTO) {
    SmsCodeDO smsCodeDO = new SmsCodeDO();
    BeanUtils.copyProperties(smsCodeDTO, smsCodeDO);

    Long smsId = snowflakeIdWorker.nextId();
    smsCodeDO.setSmsId(smsId);

    String smsCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    smsCodeDO.setVerifyCode(smsCode);

    // 15 分钟后过期
    smsCodeDO.setExpiredTime(LocalDateTime.now().plusMinutes(15));
    smsCodeDO.setCreateTime(LocalDateTime.now());
    smsCodeDO.setUpdateTime(LocalDateTime.now());
    int result = smsCodeMapper.insert(smsCodeDO);
    if (result < 0) {
      log.info("保存手机验证码失败 :: {}", smsCode);
    }
    // 发送短信验证码
    log.info("发送短信验证码 :: {}", smsCode);
    return result;
  }

  @Override
  public void checkSmsCode(SmsCodeDTO smsCodeDTO) {
    SmsCodeDO smsCodeDO = new SmsCodeDO();
    smsCodeDO.setPhone(smsCodeDTO.getPhone());
    smsCodeDO.setType(smsCodeDTO.getType());
    smsCodeDO.setSmsToken(smsCodeDTO.getSmsToken());
    smsCodeDO.setVerifyCode(smsCodeDTO.getVerifyCode());
    SmsCodeDO smsCode = smsCodeMapper.findSmsCodePhoneVerifyCodeSmsTokenNotExpired(smsCodeDO);
    if (smsCode == null) {
      throw new ProviderRpcException(ResultCode.SMS_CODE_NOT_FOUNT);
    }
    LocalDateTime expiredTime = smsCode.getExpiredTime();
    if (expiredTime != null && !expiredTime.isAfter(LocalDateTime.now())) {
      throw new ProviderRpcException(ResultCode.SMS_CODE_EXPIRED);
    }
  }
}
