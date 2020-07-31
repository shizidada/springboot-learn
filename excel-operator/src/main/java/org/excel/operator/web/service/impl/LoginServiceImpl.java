package org.excel.operator.web.service.impl;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.model.emun.LoginTypeEnum;
import org.excel.operator.model.params.LoginParam;
import org.excel.operator.utils.MapperUtils;
import org.excel.operator.utils.OkHttpClientUtil;
import org.excel.operator.web.security.component.CustomUserDetails;
import org.excel.operator.web.service.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

  private static final String OAUTH_TOKEN_URL = "http://127.0.0.1:7000/oauth/token";

  @Override public ResponseResult<Map<String, Object>> login(LoginParam loginParam) {
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
      params.put("username", loginParam.getPhone());
      params.put("smsCode", loginParam.getSmsCode());
      params.put("grant_type", LoginTypeEnum.PASSWORD.getValue());
    }

    params.put("client_id", "client");
    params.put("client_secret", "secret");

    Response response = OkHttpClientUtil.getInstance().postData(OAUTH_TOKEN_URL, params);

    Map<String, Object> result = Maps.newHashMap();
    try {
      String jsonString = Objects.requireNonNull(response.body()).string();
      Map<String, Object> authInfo = MapperUtils.json2map(jsonString);
      String accessToken = (String) authInfo.get("access_token");
      if (accessToken == null) {
        Integer code = (Integer) authInfo.get("code");
        String message = (String) authInfo.get("message");
        return new ResponseResult<Map<String, Object>>(code, message);
      }

      String refreshToken = (String) authInfo.get("refresh_token");
      result.put("access_token", accessToken);
      result.put("refresh_token", refreshToken);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseResult<>(result);
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
