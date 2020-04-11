package org.moose.business.user.service.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.moose.business.oauth.feign.OAuth2RequestTokenApi;
import org.moose.business.user.constants.OAuth2Constants;
import org.moose.business.user.model.params.LoginParam;
import org.moose.business.user.service.LoginService;
import org.moose.commons.base.code.AccountCode;
import org.moose.commons.base.code.PasswordCode;
import org.moose.commons.base.code.PhoneCode;
import org.moose.commons.base.code.SmsCode;
import org.moose.commons.base.dto.ResponseResult;
import org.moose.commons.base.dto.ResultCode;
import org.moose.commons.base.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 *
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/4/11 22:03
 * @see org.moose.business.user.service.impl
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

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
}
