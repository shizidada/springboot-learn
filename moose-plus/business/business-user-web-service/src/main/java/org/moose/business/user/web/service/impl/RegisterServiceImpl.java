package org.moose.business.user.web.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.moose.business.user.web.model.params.RegisterParam;
import org.moose.business.user.web.service.RegisterService;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.service.AccountService;
import org.moose.provider.sms.model.dto.SmsCodeDTO;
import org.moose.provider.sms.service.SmsSendService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:04
 * @see org.moose.business.user.web.service.impl
 */
@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

  @Resource
  private BCryptPasswordEncoder passwordEncoder;

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Reference(version = "1.0.0")
  private SmsSendService smsSendService;

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
      throw new BusinessException(ResultCode.PASSWORD_NOT_RIGHT);
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
}
