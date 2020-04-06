package org.moose.business.user.service.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.moose.business.oauth.feign.OAuth2RequestTokenApi;
import org.moose.business.user.constants.OAuth2Constants;
import org.moose.business.user.model.emun.SmsCodeEnum;
import org.moose.business.user.model.params.LoginParam;
import org.moose.business.user.model.params.RegisterParam;
import org.moose.business.user.model.params.SmsCodeParam;
import org.moose.business.user.service.UserService;
import org.moose.commons.base.code.AccountCode;
import org.moose.commons.base.code.PasswordCode;
import org.moose.commons.base.code.PhoneCode;
import org.moose.commons.base.code.SmsCode;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.moose.configuration.annotation.EnumValidator;
import org.moose.configuration.annotation.EnumValidatorClass;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.service.AccountService;
import org.moose.provider.sms.model.dto.SmsCodeDTO;
import org.moose.provider.sms.service.SmsSendService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-24 13:31:13:31
 * @see org.moose.business.user.service.impl
 */
@Slf4j
@Component
public class UserServiceImpl implements UserService {

  @Resource
  private BCryptPasswordEncoder passwordEncoder;

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Reference(version = "1.0.0")
  private SmsSendService smsSendService;

  //@Resource
  //private RocketMQTemplate rocketMQTemplate;
  //@Value("${mq.sms.topic}")
  //private String topic;
  //@Value("${mq.sms.tag}")
  //private String tag;

  @Resource
  private OAuth2RequestTokenApi oAuth2RequestTokenApi;

  @Override public ResponseResult<?> login(LoginParam loginParam) {

    /**
     * 检查登录信息
     * @see EnumValidator
     * @see EnumValidatorClass#isValid(Object, ConstraintValidatorContext)
     */

    // 封装请求授权参数
    Map<String, String> param = Maps.newHashMap();

    String loginType = loginParam.getLoginType();
    // password type
    if (loginType.equals(OAuth2Constants.OAUTH2_PASSWORD_GRANT_TYPE)) {
      String accountName = loginParam.getAccountName();
      if (StringUtils.isBlank(accountName)) {
        throw new BusinessException(AccountCode.ACCOUNT_MUST_NOT_BE_NULL.getCode(),
            AccountCode.ACCOUNT_MUST_NOT_BE_NULL.getMessage());
      }

      String password = loginParam.getPassword();
      if (StringUtils.isBlank(password)) {
        throw new BusinessException(PasswordCode.PASSWORD_MUST_NOT_BE_NULL.getCode(),
            PasswordCode.PASSWORD_MUST_NOT_BE_NULL.getMessage());
      }
      param.put("username", accountName);
      param.put("password", password);
      param.put("grant_type", OAuth2Constants.OAUTH2_PASSWORD_GRANT_TYPE);
    }

    // sms_code
    if (loginType.equals(OAuth2Constants.OAUTH2_SMS_GRANT_TYPE)) {
      String phone = loginParam.getPhone();

      // 手机号码
      if (StringUtils.isBlank(phone)) {
        throw new BusinessException(PhoneCode.PHONE_MUST_NOT_BE_NULL.getCode(),
            PhoneCode.PHONE_MUST_NOT_BE_NULL.getMessage());
      }

      // 校验短信验证码
      String smsCode = loginParam.getSmsCode();
      if (StringUtils.isBlank(smsCode)) {
        throw new BusinessException(SmsCode.SMS_CODE_MUST_NOT_BE_NULL.getCode(),
            SmsCode.SMS_CODE_MUST_NOT_BE_NULL.getMessage());
      }

      param.put("phone", phone);
      param.put("smsCode", smsCode);
      param.put("grant_type", OAuth2Constants.OAUTH2_SMS_GRANT_TYPE);
    }

    param.put("client_id", OAuth2Constants.OAUTH2_CLIENT_ID);
    param.put("client_secret", OAuth2Constants.OAUTH2_CLIENT_SECRET);

    Map<String, Object> authToken = oAuth2RequestTokenApi.getOAuthToken(param);
    if (authToken == null) {
      return new ResponseResult<Map<String, Object>>(ResultCode.NETWORK_UNKNOWN.getCode(),
          ResultCode.NETWORK_UNKNOWN.getMessage());
    }

    String accessToken = (String) authToken.get("access_token");
    if (accessToken == null) {
      Integer code = (Integer) authToken.get("code");
      String message = (String) authToken.get("message");
      return new ResponseResult<Map<String, Object>>(code, message);
    }

    String refreshToken = (String) authToken.get("refresh_token");
    Map<String, Object> result = Maps.newHashMap();
    result.put("access_token", accessToken);
    result.put("refresh_token", refreshToken);
    return new ResponseResult<>(result);
  }

  @Override
  public ResponseResult<?> register(RegisterParam registerParam) {
    // 校验注册信息
    checkRegisterInfo(registerParam);

    // 保存注册账号信息
    boolean result = saveRegisterInfo(registerParam);

    return new ResponseResult<>(result);
  }

  /**
   * 校验密码
   */
  private void checkRegisterInfo(RegisterParam registerParam) {
    String password = registerParam.getPassword();
    String rePassword = registerParam.getRePassword();
    if (!password.equals(rePassword)) {
      throw new BusinessException(PasswordCode.PASSWORD_NOT_RIGHT.getCode(),
          PasswordCode.PASSWORD_NOT_RIGHT.getMessage());
    }

    // 检查短信校验码是否正确
    // TODO: 1、校验注册的手机、账号是否被注册？？

    // TODO: 2、校验短信验证码是否正确
    SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
    smsCodeDTO.setPhone(registerParam.getPhone());
    smsCodeDTO.setVerifyCode(registerParam.getVerifyCode());
    smsCodeDTO.setSmsToken(registerParam.getSmsToken());
    smsCodeDTO.setType(registerParam.getType());
    smsSendService.checkSmsCode(smsCodeDTO);
  }

  /**
   * 保存注册信息
   */
  private boolean saveRegisterInfo(RegisterParam registerParam) {
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setAccountName(registerParam.getAccountName());
    accountDTO.setPhone(registerParam.getPhone());
    accountDTO.setSourceType(registerParam.getSourceType());

    PasswordDTO passwordDTO = new PasswordDTO();
    passwordDTO.setPassword(registerParam.getPassword());
    return accountService.add(accountDTO, passwordDTO);
  }

  @Override public ResponseResult<?> logout(String accessToken) {
    boolean result = oAuth2RequestTokenApi.deleteToken(accessToken);
    return new ResponseResult<>(result);
  }

  @Override public boolean findByPhone(String phone) {
    if (phone == null || StringUtils.isBlank(phone)) {
      throw new BusinessException(PhoneCode.PHONE_MUST_NOT_BE_NULL.getCode(),
          PhoneCode.PHONE_MUST_NOT_BE_NULL.getMessage());
    }
    AccountDTO accountDTO = accountService.getAccountByPhone(phone);
    return accountDTO != null;
  }

  @Override public ResponseResult<?> sendSmsCode(SmsCodeParam smsCodeParam) {
    boolean result = this.findByPhone(smsCodeParam.getPhone());

    String type = smsCodeParam.getType();
    // 发送短信 判断 type : register，手机号码不能存在
    if (SmsCodeEnum.REGISTER.getValue().equals(type)) {
      if (result) {
        throw new BusinessException(PhoneCode.PHONE_IS_EXIST.getCode(),
            PhoneCode.PHONE_IS_EXIST.getMessage());
      }
    }

    // 重置密码，手机号码必须存在
    if (SmsCodeEnum.REST_PASSWORD.getValue().equals(type)) {
      if (!result) {
        throw new BusinessException(PhoneCode.PHONE_NOT_FOUND.getCode(),
            PhoneCode.PHONE_NOT_FOUND.getMessage());
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
        throw new BusinessException(SmsCode.SMS_CODE_SEND_FAIL.getCode(),
            SmsCode.SMS_CODE_SEND_FAIL.getMessage());
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
