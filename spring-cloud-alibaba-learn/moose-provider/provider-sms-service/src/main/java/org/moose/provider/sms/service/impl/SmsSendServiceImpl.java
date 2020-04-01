package org.moose.provider.sms.service.impl;

import com.alibaba.fastjson.JSON;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.moose.provider.sms.mapper.SmsCodeMapper;
import org.moose.provider.sms.model.domain.SmsCodeDO;
import org.moose.provider.sms.model.dto.SmsCodeDTO;
import org.moose.provider.sms.service.SmsSendService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 *
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
public class SmsSendServiceImpl implements SmsSendService {

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Resource
  private SmsCodeMapper smsCodeMapper;

  @Override
  public int add(String jsonStr) {
    if (jsonStr == null || StringUtils.isBlank(jsonStr)) {
      return 0;
    }

    SmsCodeDTO smsCodeDTO = JSON.parseObject(jsonStr, SmsCodeDTO.class);
    if (smsCodeDTO == null) {
      return 0;
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
      log.info("保存手机验证码失败");
      return 0;
    }
    // 发送短信验证码
    log.info("发送短信验证码 :: {}", smsCode);
    return result;
  }
}
