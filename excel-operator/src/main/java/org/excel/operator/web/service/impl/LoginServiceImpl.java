package org.excel.operator.web.service.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.constants.RedisKeyConstants;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.model.emun.LoginTypeEnum;
import org.excel.operator.model.params.LoginParam;
import org.excel.operator.utils.MapperUtils;
import org.excel.operator.utils.OkHttpClientUtil;
import org.excel.operator.web.security.component.CustomUserDetails;
import org.excel.operator.web.service.LoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

  @Resource
  private RedisTemplate<String, String> redisTemplate;

  private static final String OAUTH_TOKEN_URL = "http://127.0.0.1:7000/oauth/token";

  /**
   * localhost:7000/oauth/token?grant_type=sms_code&client_id=client&client_secret=secret&mobile=13511083015&smsCode=123456
   */
  @Override public ResponseResult<Object> login(LoginParam loginParam) {
    // 通过 HTTP 客户端请求登录接口
    Map<String, String> params = Maps.newHashMap();

    // 登录方式
    String loginType = loginParam.getLoginType();

    if (LoginTypeEnum.PASSWORD.getValue().equals(loginType)) {
      String accountName = loginParam.getAccountName();
      if (StringUtils.isEmpty(accountName)) {
        throw new BusinessException(ResultCode.ACCOUNT_NOT_EMPTY);
      }

      String password = loginParam.getPassword();
      if (StringUtils.isEmpty(password)) {
        throw new BusinessException(ResultCode.PASSWORD_NOT_EMPTY);
      }

      params.put("username", accountName);
      params.put("password", password);
      params.put("grant_type", LoginTypeEnum.PASSWORD.getValue());
    }

    if (LoginTypeEnum.SMS_CODE.getValue().equals(loginType)) {
      params.put("mobile", loginParam.getPhone());
      params.put("smsCode", loginParam.getSmsCode());
      params.put("grant_type", LoginTypeEnum.SMS_CODE.getValue());
    }

    params.put("client_id", "client");
    params.put("client_secret", "secret");

    Response response = OkHttpClientUtil.getInstance().postData(OAUTH_TOKEN_URL, params);
    try {
      String jsonString = Objects.requireNonNull(response.body()).string();
      Map<String, Object> authInfo = MapperUtils.json2map(jsonString);
      String accessToken = (String) authInfo.get("access_token");
      if (accessToken == null) {
        Integer code = (Integer) authInfo.get("code");
        String message = (String) authInfo.get("message");
        return new ResponseResult<>(code, message);
      }
      String refreshToken = (String) authInfo.get("refresh_token");
      redisTemplate.opsForValue()
          .set(RedisKeyConstants.REFRESH_TOKEN_KEY + accessToken, refreshToken);
      return new ResponseResult<>(accessToken);
    } catch (Exception e) {
      log.info("调用 /oauth/token get token 失败; {}", e.getMessage());
      throw new BusinessException(e.getMessage());
    }
  }

  @Override public ResponseResult<Object> getRefreshTokenByAccessToken(String accessToken) {
    if (null == accessToken) {
      throw new BusinessException(ResultCode.ACCESS_TOKEN_EMPTY);
    }
    String refreshToken =
        redisTemplate.opsForValue().get(RedisKeyConstants.REFRESH_TOKEN_KEY + accessToken);
    // TODO: IP 限制
    if (null == refreshToken) {
      throw new BusinessException(ResultCode.REFRESH_TOKEN_NOT_EXIST);
    }
    return new ResponseResult<>(refreshToken);
  }

  /**
   * http://localhost:7000/oauth/token?grant_type=refresh_token&refresh_token=bc3721b0-9611-471f-867c-1259022614bc&client_id=client&client_secret=secret
   */
  @Override public ResponseResult<Object> getAccessTokenByRefreshToken(String refresh_token) {

    if (null == refresh_token) {
      throw new BusinessException(ResultCode.TOKEN_VALIDATE_EMPTY);
    }

    // 通过 HTTP 客户端请求登录接口
    Map<String, String> params = Maps.newHashMap();

    params.put("client_id", "client");
    params.put("client_secret", "secret");
    params.put("grant_type", "refresh_token");
    params.put("refresh_token", refresh_token);
    Response response = OkHttpClientUtil.getInstance().postData(OAUTH_TOKEN_URL, params);
    try {
      String jsonString = Objects.requireNonNull(response.body()).string();
      Map<String, Object> authInfo = MapperUtils.json2map(jsonString);
      String accessToken = (String) authInfo.get("access_token");
      if (accessToken == null) {
        Integer code = (Integer) authInfo.get("code");
        String message = (String) authInfo.get("message");
        return new ResponseResult<>(code, message);
      }
      String refreshToken = (String) authInfo.get("refresh_token");
      redisTemplate.opsForValue()
          .set(RedisKeyConstants.REFRESH_TOKEN_KEY + accessToken, refreshToken);

      return new ResponseResult<>(accessToken);
    } catch (Exception e) {
      log.info("调用 /oauth/token refresh_token 失败; {}", e.getMessage());
      throw new BusinessException(e.getMessage());
    }
  }

  @Override
  public boolean isLogin() {
    Authentication authentication = (Authentication) this.getAuthentication();
    if (authentication == null) {
      return false;
    }
    Object principal = authentication.getPrincipal();
    return principal instanceof CustomUserDetails;
  }

  @Override public Object getPrincipal() {
    Authentication authentication = (Authentication) this.getAuthentication();
    if (authentication != null) {
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
