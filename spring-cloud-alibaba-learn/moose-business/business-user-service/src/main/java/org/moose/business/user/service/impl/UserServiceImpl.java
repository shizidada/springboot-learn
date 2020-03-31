package org.moose.business.user.service.impl;

import com.google.common.collect.Maps;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.moose.business.oauth.feign.OAuth2RequestTokenApi;
import org.moose.business.user.constants.OAuth2Constants;
import org.moose.business.user.model.params.LoginParam;
import org.moose.business.user.model.params.RegisterParam;
import org.moose.business.user.service.UserService;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.PasswordDTO;
import org.moose.provider.account.model.dto.RoleDTO;
import org.moose.provider.account.service.AccountService;
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
@Component
public class UserServiceImpl implements UserService {

  @Resource
  private BCryptPasswordEncoder passwordEncoder;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Reference(version = "1.0.0")
  private AccountService accountService;

  @Resource
  private OAuth2RequestTokenApi oAuth2RequestTokenApi;

  @Override public ResponseResult<?> login(LoginParam loginParam) {
    String loginType = loginParam.getLoginType();

    // 登录方式是否为空
    if (StringUtils.isBlank(loginType)) {
      throw new BusinessException(ResultCode.LOGIN_TYPE_MUST_NOT_BE_NULL.getCode(),
          ResultCode.LOGIN_TYPE_MUST_NOT_BE_NULL.getMessage());
    }

    // 登录方式 password / sms_code
    if (!loginType.equals(OAuth2Constants.OAUTH2_PASSWORD_GRANT_TYPE) && !loginType.equals(
        OAuth2Constants.OAUTH2_SMS_GRANT_TYPE)) {
      throw new BusinessException(ResultCode.LOGIN_TYPE_ERROR.getCode(),
          ResultCode.LOGIN_TYPE_ERROR.getMessage());
    }

    // 封装请求授权参数
    Map<String, String> param = Maps.newHashMap();

    // password type
    if (loginType.equals(OAuth2Constants.OAUTH2_PASSWORD_GRANT_TYPE)) {
      String accountName = loginParam.getAccountName();
      if (StringUtils.isBlank(accountName)) {
        throw new BusinessException(ResultCode.ACCOUNT_MUST_NOT_BE_NULL.getCode(),
            ResultCode.ACCOUNT_MUST_NOT_BE_NULL.getMessage());
      }

      String password = loginParam.getPassword();
      if (StringUtils.isBlank(password)) {
        throw new BusinessException(ResultCode.PASSWORD_MUST_NOT_BE_NULL.getCode(),
            ResultCode.PASSWORD_MUST_NOT_BE_NULL.getMessage());
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
        throw new BusinessException(ResultCode.PHONE_MUST_NOT_BE_NULL.getCode(),
            ResultCode.PHONE_MUST_NOT_BE_NULL.getMessage());
      }

      // 校验短信验证码
      String smsCode = loginParam.getSmsCode();
      if (StringUtils.isBlank(smsCode)) {
        throw new BusinessException(ResultCode.SMS_CODE_MUST_NOT_BE_NULL.getCode(),
            ResultCode.SMS_CODE_MUST_NOT_BE_NULL.getMessage());
      }

      param.put("phone", phone);
      param.put("smsCode", smsCode);
      param.put("grant_type", OAuth2Constants.OAUTH2_SMS_GRANT_TYPE);
    }

    param.put("client_id", OAuth2Constants.OAUTH2_CLIENT_ID);
    param.put("client_secret", OAuth2Constants.OAUTH2_CLIENT_SECRET);

    Map<String, Object> authToken = oAuth2RequestTokenApi.getOAuthToken(param);
    if (authToken == null) {
      return new ResponseResult<Map<String, Object>>(ResultCode.NET_WORK_UNKNOWN.getCode(),
          ResultCode.NET_WORK_UNKNOWN.getMessage());
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

  @Override public ResponseResult<?> register(RegisterParam registerParam) {
    String password = registerParam.getPassword();
    String rePassword = registerParam.getRePassword();
    if (password.equals(rePassword)) {
      throw new BusinessException(ResultCode.PASSWORD_NOT_RIGHT.getCode(),
          ResultCode.PASSWORD_NOT_RIGHT.getMessage());
    }

    Long accountId = snowflakeIdWorker.nextId();
    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setAccountId(accountId);
    accountDTO.setAccountName(registerParam.getAccountName());
    accountDTO.setPhone(registerParam.getPhone());
    accountDTO.setCreateTime(LocalDateTime.now());
    accountDTO.setUpdateTime(LocalDateTime.now());

    Long passwordId = snowflakeIdWorker.nextId();
    PasswordDTO passwordDTO = new PasswordDTO();
    passwordDTO.setAccountId(accountId);
    passwordDTO.setPassword(passwordEncoder.encode(registerParam.getPassword()));
    passwordDTO.setPasswordId(passwordId);
    passwordDTO.setCreateTime(LocalDateTime.now());
    passwordDTO.setUpdateTime(LocalDateTime.now());

    Long roleId = snowflakeIdWorker.nextId();
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setRoleId(roleId);
    roleDTO.setRole("USER");
    roleDTO.setAccountId(accountId);
    roleDTO.setCreateTime(LocalDateTime.now());
    roleDTO.setUpdateTime(LocalDateTime.now());

    boolean result = accountService.add(accountDTO, passwordDTO, roleDTO);
    return new ResponseResult<>(result);
  }

  @Override public ResponseResult<?> logout(String accessToken) {
    boolean result = oAuth2RequestTokenApi.deleteToken(accessToken);
    return new ResponseResult<>(result);
  }
}
