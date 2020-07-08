package org.moose.business.user.web.service.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.moose.business.user.web.model.emun.SmsCodeEnum;
import org.moose.business.user.web.model.params.SmsCodeParam;
import org.moose.business.user.web.service.SmsCodeService;
import org.moose.business.user.web.service.UserBaseService;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.sms.model.dto.SmsCodeDTO;
import org.moose.provider.sms.service.SmsSendService;
import org.springframework.stereotype.Service;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:15
 * @see org.moose.business.user.web.service.impl
 */
@Service
@Slf4j
public class SmsCodeServiceImpl implements SmsCodeService {

  @Resource
  private UserBaseService userBaseService;

  @Reference(version = "1.0.0")
  private SmsSendService smsSendService;

  @Override public ResponseResult<?> sendSmsCode(SmsCodeParam smsCodeParam) {
    AccountDTO accountDTO = userBaseService.findByPhone(smsCodeParam.getPhone());

    String type = smsCodeParam.getType();
    // 发送短信 判断 type : register，手机号码不能存在
    if (SmsCodeEnum.REGISTER.getValue().equals(type)) {
      if (accountDTO != null) {
        throw new BusinessException(ResultCode.PHONE_IS_EXIST);
      }
    }

    // 重置密码，手机号码必须存在
    if (SmsCodeEnum.REST_PASSWORD.getValue().equals(type)) {
      if (accountDTO == null) {
        throw new BusinessException(ResultCode.PHONE_NOT_FOUND);
      }
    }

    // TODO: 检查一天发送的短信是否上限
    checkSmsCodeSuperiorLimit();

    // 查询是否存在手机号码
    SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
    smsCodeDTO.setType(smsCodeParam.getType());
    smsCodeDTO.setPhone(smsCodeParam.getPhone());
    String smsToken = UUID.randomUUID().toString().replace("-", "");
    smsCodeDTO.setSmsToken(smsToken);

    try {
      // TODO: MQ 发送短信信息
      //Message msg = new Message(topic, tag, "sms-code", JSON.toJSONString(smsCodeDTO).getBytes());
      //SendResult sendResult = rocketMQTemplate.getProducer().send(msg);
      //SendStatus sendStatus = sendResult.getSendStatus();
      //if (sendStatus != SendStatus.SEND_OK) {
      //  throw new BusinessException(SmsCode.SMS_CODE_SEND_FAIL.getCode(),
      //      SmsCode.SMS_CODE_SEND_FAIL.getMessage());
      //}

      int smsResult = smsSendService.addSmsCode(smsCodeDTO);
      if (smsResult < 0) {
        throw new BusinessException(ResultCode.SMS_CODE_SEND_FAIL);
      }
      Map<String, Object> map = Maps.newHashMap();
      map.put("sms_token", smsToken);
      return new ResponseResult<Map<String, Object>>(map);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseResult<Map<String, Object>>(ResultCode.NETWORK_UNKNOWN.getCode(),
          e.getMessage());
    }
  }

  /**
   * TODO: 检查一天发送的短信是否上限
   */
  private void checkSmsCodeSuperiorLimit() {

  }
}
