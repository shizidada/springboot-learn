package org.moose.operator.web.service.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResponseResult;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.model.emun.LoginTypeEnum;
import org.moose.operator.model.params.LoginParam;
import org.moose.operator.util.MapperUtils;
import org.moose.operator.util.OkHttpClientUtils;
import org.moose.operator.web.security.component.CustomUserDetails;
import org.moose.operator.web.service.LoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

/**
 * @author taohua
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

  private static final String OAUTH_TOKEN_URL = "http://127.0.0.1:7000/oauth/token";

  @Resource
  private RedisTemplate<String, String> redisTemplate;

  /**
   * localhost:7000/oauth/token?grant_type=sms_code&client_id=client&client_secret=secret&mobile=13511083015&smsCode=123456
   */
  @Override public ResponseResult<Object> login(LoginParam loginParam) {
    // 通过 HTTP 客户端请求登录接口
    Map<String, String> params = Maps.newHashMap();

    // 登录方式
    String loginType = loginParam.getLoginType();
    if (StringUtils.isEmpty(loginType)) {
      throw new BusinessException(ResultCode.LOGIN_METHOD_IS_EMPTY);
    }

    // 密码方式登录
    if (LoginTypeEnum.PASSWORD.getValue().equals(loginType)) {
      String accountName = loginParam.getAccountName();
      if (StringUtils.isEmpty(accountName)) {
        throw new BusinessException(ResultCode.ACCOUNT_IS_EMPTY);
      }

      String password = loginParam.getPassword();
      if (StringUtils.isEmpty(password)) {
        throw new BusinessException(ResultCode.PASSWORD_IS_EMPTY);
      }

      params.put("username", accountName);
      params.put("password", password);
      params.put("grant_type", loginType);
    }

    // 短信方式登录
    if (LoginTypeEnum.SMS_CODE.getValue().equals(loginType)) {

      String mobile = loginParam.getPhone();
      if (StringUtils.isEmpty(mobile)) {
        throw new BusinessException(ResultCode.PHONE_NUMBER_IS_EMPTY);
      }

      String smsCode = loginParam.getSmsCode();
      if (StringUtils.isEmpty(smsCode)) {
        throw new BusinessException(ResultCode.SMS_CODE_IS_EMPTY);
      }

      params.put("mobile", mobile);
      params.put("smsCode", smsCode);
      params.put("grant_type", loginType);
    }

    params.put("client_id", SecurityConstants.OAUTH2_CLIENT);
    params.put("client_secret", SecurityConstants.OAUTH2_SECRET);

    Response response = OkHttpClientUtils.getInstance().postData(OAUTH_TOKEN_URL, params);
    try {
      String jsonString = Objects.requireNonNull(response.body()).string();
      Map<String, Object> authInfo = MapperUtils.json2map(jsonString);
      String accessToken = (String) authInfo.get(OAuth2AccessToken.ACCESS_TOKEN);
      if (StringUtils.isEmpty(accessToken)) {
        Integer code = (Integer) authInfo.get("code");
        String message = (String) authInfo.get("message");
        return new ResponseResult<>(code, message);
      }

      // save refresh token redis
      String refreshToken = (String) authInfo.get(OAuth2AccessToken.REFRESH_TOKEN);
      redisTemplate.opsForValue()
          .set(RedisKeyConstants.REFRESH_TOKEN_KEY + accessToken, refreshToken);
      return new ResponseResult<>(accessToken, "获取 access token 成功");
    } catch (Exception e) {
      log.info("调用 /oauth/token get token 失败; {}", e.getMessage());
      throw new BusinessException(e.getMessage());
    }
  }

  @Override public ResponseResult<Object> getRefreshTokenByAccessToken(String accessToken) {
    if (StringUtils.isEmpty(accessToken)) {
      throw new BusinessException(ResultCode.ACCESS_TOKEN_IS_EMPTY);
    }
    String refreshToken =
        redisTemplate.opsForValue().get(RedisKeyConstants.REFRESH_TOKEN_KEY + accessToken);
    // TODO: IP 限制
    if (StringUtils.isEmpty(refreshToken)) {
      throw new BusinessException(ResultCode.REFRESH_TOKEN_NOT_EXIST);
    }
    return new ResponseResult<>(refreshToken, "获取 refresh token 成功");
  }

  /**
   * http://localhost:7000/oauth/token?grant_type=refresh_token&refresh_token=bc3721b0-9611-471f-867c-1259022614bc&client_id=client&client_secret=secret
   */
  @Override public ResponseResult<Object> getAccessTokenByRefreshToken(String refreshTokenParam) {

    if (StringUtils.isEmpty(refreshTokenParam)) {
      throw new BusinessException(ResultCode.TOKEN_IS_EMPTY);
    }

    // 通过 HTTP 客户端请求登录接口
    Map<String, String> params = Maps.newHashMap();

    params.put("client_id", SecurityConstants.OAUTH2_CLIENT);
    params.put("client_secret", SecurityConstants.OAUTH2_SECRET);
    params.put("grant_type", OAuth2AccessToken.REFRESH_TOKEN);
    params.put("refresh_token", refreshTokenParam);
    Response response = OkHttpClientUtils.getInstance().postData(OAUTH_TOKEN_URL, params);
    try {
      String jsonString = Objects.requireNonNull(response.body()).string();
      Map<String, Object> authInfo = MapperUtils.json2map(jsonString);
      String accessToken = (String) authInfo.get(OAuth2AccessToken.ACCESS_TOKEN);
      if (null == accessToken) {
        Integer code = (Integer) authInfo.get("code");
        String message = (String) authInfo.get("message");
        return new ResponseResult<>(code, message);
      }

      // save redis
      String refreshToken = (String) authInfo.get(OAuth2AccessToken.REFRESH_TOKEN);
      redisTemplate.opsForValue()
          .set(RedisKeyConstants.REFRESH_TOKEN_KEY + accessToken, refreshToken);

      return new ResponseResult<>(accessToken, "获取 access token 成功");
    } catch (Exception e) {
      log.info("调用 /oauth/token refresh_token 失败; {}", e.getMessage());
      throw new BusinessException(e.getMessage());
    }
  }

  @Override
  public ResponseResult<Object> isLogin() {
    Authentication authentication = (Authentication) this.getAuthentication();
    if (ObjectUtils.isEmpty(authentication)) {
      return new ResponseResult<>(false, "获取登录状态成功");
    }
    Object principal = authentication.getPrincipal();
    return new ResponseResult<>(principal instanceof CustomUserDetails, "获取登录状态成功");
  }

  @Override public Object getPrincipal() {
    Authentication authentication = (Authentication) this.getAuthentication();
    if (ObjectUtils.isNotEmpty(authentication)) {
      return authentication.getPrincipal();
    }
    return null;
  }

  /**
   * 获取登录信息
   *
   * @return Authentication
   */
  private Object getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
