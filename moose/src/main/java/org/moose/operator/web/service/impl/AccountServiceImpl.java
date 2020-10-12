package org.moose.operator.web.service.impl;

import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.constant.SecurityConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.mapper.AccountMapper;
import org.moose.operator.model.domain.AccountDO;
import org.moose.operator.model.domain.UserInfoDO;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.dto.PasswordDTO;
import org.moose.operator.model.emun.LoginTypeEnum;
import org.moose.operator.model.params.LoginInfoParam;
import org.moose.operator.model.params.RegisterInfoParam;
import org.moose.operator.util.MapperUtils;
import org.moose.operator.util.OkHttpClientUtils;
import org.moose.operator.util.SnowflakeIdWorker;
import org.moose.operator.web.security.component.CustomUserDetails;
import org.moose.operator.web.service.AccountService;
import org.moose.operator.web.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/17 13:10
 * @see org.moose.operator.web.service.impl
 */

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

  private static final String OAUTH_TOKEN_URL = "http://127.0.0.1:7000/oauth/token";

  @Resource
  private RedisTemplate<String, String> redisTemplate;

  /**
   * Error creating bean with name 'tokenStore': Requested bean is currently in creation: Is there
   * an unresolvable circular reference?
   * <p>
   * use @Lazy
   */
  @Resource
  private RedisTokenStore tokenStore;

  @Resource
  private AccountMapper accountMapper;

  @Resource
  private PasswordServiceImpl passwordService;

  @Resource
  private UserInfoService userInfoService;

  @Resource
  private PasswordEncoder passwordEncoder;

  @Resource
  private SnowflakeIdWorker snowflakeIdWorker;

  @Override public AccountDTO getByAccountName(String accountName) {
    accountName = accountName.trim();
    if (StringUtils.isEmpty(accountName)) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    AccountDO accountDO = accountMapper.findByAccountName(accountName);
    if (accountDO == null) {
      throw new BusinessException(ResultCode.ACCOUNT_OR_PASSWORD_ERROR);
    }

    AccountDTO accountDTO = new AccountDTO();
    BeanUtils.copyProperties(accountDO, accountDTO);
    return accountDTO;
  }

  @Override public AccountDTO getAccountByPhone(String phone) {
    AccountDO accountDO = accountMapper.findByPhone(phone);
    if (accountDO == null) {
      throw new BusinessException(ResultCode.PHONE_NOT_EXITS);
    }
    AccountDTO accountDTO = new AccountDTO();
    BeanUtils.copyProperties(accountDO, accountDTO);
    return accountDTO;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public Boolean saveAccount(HttpServletRequest request,
      RegisterInfoParam registerInfo) {
    String password = registerInfo.getPassword();
    String rePassword = registerInfo.getRePassword();
    String url = request.getRequestURL().toString();
    String ip = request.getRemoteAddr();
    log.info("register [ip {}], [url {}]", ip, url);

    // 比对两次密码是否一致
    if (!StringUtils.equals(password, rePassword)) {
      throw new BusinessException(ResultCode.PASSWORD_ERROR);
    }

    // 查询对应 账号名称
    String accountName = registerInfo.getAccountName();
    AccountDO account = accountMapper.findByAccountName(accountName);
    if (account != null) {
      throw new BusinessException(ResultCode.ACCOUNT_NAME_EXITS);
    }

    // 查询手机号是否存在
    String phone = registerInfo.getPhone();
    account = accountMapper.findByPhone(phone);
    if (account != null) {
      throw new BusinessException(ResultCode.PHONE_EXITS);
    }

    try {
      AccountDO accountDO = new AccountDO();
      accountDO.setAccountId(String.valueOf(snowflakeIdWorker.nextId()));
      accountDO.setAccountName(registerInfo.getAccountName());
      accountDO.setPhone(registerInfo.getPhone());

      PasswordDTO passwordDTO = new PasswordDTO();
      passwordDTO.setAccountId(accountDO.getAccountId());
      passwordDTO.setPasswordId(String.valueOf(snowflakeIdWorker.nextId()));
      // 加密密码
      passwordDTO.setPassword(passwordEncoder.encode(registerInfo.getPassword()));

      accountMapper.insertAccount(accountDO);
      passwordService.savePassword(passwordDTO);

      UserInfoDO userInfoDO = new UserInfoDO();
      userInfoDO.setPhone(phone);
      userInfoDO.setUserId(String.valueOf(snowflakeIdWorker.nextId()));
      userInfoDO.setUserName(accountName);
      userInfoDO.setAccountId(accountDO.getAccountId());
      userInfoDO.setAccountName(accountName);
      userInfoDO.setGender(registerInfo.getGender());
      userInfoDO.setAvatar(registerInfo.getAvatar());
      userInfoDO.setCreateTime(LocalDateTime.now());
      userInfoDO.setUpdateTime(LocalDateTime.now());

      userInfoService.saveUserInfo(userInfoDO);
    } catch (Exception e) {
      log.info("register failed error [{}]", e.getMessage());
      throw new BusinessException(ResultCode.REGISTER_FAIL);
    }
    return true;
  }

  @Override
  public Boolean removeToken(String accessToken) {
    if (StringUtils.isEmpty(accessToken)) {
      throw new BusinessException(ResultCode.ACCESS_TOKEN_IS_EMPTY);
    }

    OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
    if (ObjectUtils.isEmpty(oAuth2AccessToken)) {
      throw new BusinessException(ResultCode.TOKEN_VALIDATE_FAIL);
    }

    String refreshTokenKey = String.format(RedisKeyConstants.REFRESH_TOKEN_KEY, accessToken);
    Boolean refreshTokenExist = redisTemplate.hasKey(refreshTokenKey);
    if (ObjectUtils.isEmpty(refreshTokenExist)) {
      throw new BusinessException(ResultCode.TOKEN_VALIDATE_FAIL);
    }

    // remove oauth2.0 token
    String refreshToken = redisTemplate.opsForValue().get(refreshTokenKey);
    tokenStore.removeAccessToken(accessToken);
    tokenStore.removeRefreshToken(refreshToken);

    // remove refresh Token
    redisTemplate.expire(refreshTokenKey, 0, TimeUnit.SECONDS);

    return true;
  }

  /**
   * localhost:7000/oauth/token?grant_type=sms_code&client_id=client&client_secret=secret&phone=13500181521&smsCode=123456
   * localhost:7000/oauth/token?grant_type=password&client_id=client&client_secret=secret&accountName=tom&password=123456
   */
  @Override public String getToken(LoginInfoParam loginInfoParam) {
    // 通过 HTTP 客户端请求登录接口
    Map<String, String> params = Maps.newHashMap();

    // 登录方式
    String loginType = loginInfoParam.getLoginType();
    if (StringUtils.isEmpty(loginType)) {
      throw new BusinessException(ResultCode.LOGIN_METHOD_IS_EMPTY);
    }

    // 密码方式登录
    if (LoginTypeEnum.PASSWORD.getValue().equals(loginType)) {
      String accountName = loginInfoParam.getAccountName();
      if (StringUtils.isEmpty(accountName)) {
        throw new BusinessException(ResultCode.ACCOUNT_IS_EMPTY);
      }

      String password = loginInfoParam.getPassword();
      if (StringUtils.isEmpty(password)) {
        throw new BusinessException(ResultCode.PASSWORD_IS_EMPTY);
      }

      params.put("username", accountName);
      params.put("password", password);
      params.put("grant_type", loginType);
    }

    // 短信方式登录
    if (LoginTypeEnum.SMS_CODE.getValue().equals(loginType)) {

      String phoneNumber = loginInfoParam.getPhone();
      if (StringUtils.isEmpty(phoneNumber)) {
        throw new BusinessException(ResultCode.PHONE_NUMBER_IS_EMPTY);
      }

      String smsCode = loginInfoParam.getSmsCode();
      if (StringUtils.isEmpty(smsCode)) {
        throw new BusinessException(ResultCode.SMS_CODE_IS_EMPTY);
      }

      params.put("phone", phoneNumber);
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
        throw new BusinessException(message, code);
      }

      // save refresh token redis
      String refreshToken = (String) authInfo.get(OAuth2AccessToken.REFRESH_TOKEN);
      redisTemplate.opsForValue()
          .set(String.format(RedisKeyConstants.REFRESH_TOKEN_KEY, accessToken), refreshToken);
      return accessToken;
    } catch (Exception e) {
      log.info("调用 /oauth/token get token 失败; {}", e.getMessage());
      Integer code = ResultCode.LOGIN_SERVER_ERROR.getCode();
      if (e instanceof BusinessException) {
        BusinessException be = (BusinessException) e;
        code = be.getCode();
      }
      throw new BusinessException(e.getMessage(), code);
    }
  }

  @Override public AccountDTO getAccountInfo() {
    Object principal = this.getPrincipal();
    if (!(principal instanceof CustomUserDetails)) {
      throw new BusinessException(ResultCode.USER_INFO_NOT_EXIST);
    }

    CustomUserDetails userDetails = (CustomUserDetails) principal;
    AccountDTO accountDTO = userDetails.getAccountDTO();
    if (ObjectUtils.isEmpty(accountDTO)) {
      throw new BusinessException(ResultCode.USER_INFO_NOT_EXIST);
    }
    return accountDTO;
  }

  @Override public String getRefreshTokenByAccessToken(String accessToken) {
    if (StringUtils.isEmpty(accessToken)) {
      throw new BusinessException(ResultCode.ACCESS_TOKEN_IS_EMPTY);
    }
    String accessTokenKey = String.format(RedisKeyConstants.REFRESH_TOKEN_KEY, accessToken);

    String refreshToken = redisTemplate.opsForValue().get(accessTokenKey);

    // TODO: IP 限制
    if (StringUtils.isEmpty(refreshToken)) {
      throw new BusinessException(ResultCode.REFRESH_TOKEN_NOT_EXIST);
    }

    redisTemplate.expire(accessTokenKey, 0, TimeUnit.SECONDS);

    return refreshToken;
  }

  /**
   * http://localhost:7000/oauth/token?grant_type=refresh_token&refresh_token=bc3721b0-9611-471f-867c-1259022614bc&client_id=client&client_secret=secret
   */
  @Override public String getAccessTokenByRefreshToken(String refreshTokenParam) {

    if (StringUtils.isEmpty(refreshTokenParam)) {
      throw new BusinessException(ResultCode.TOKEN_IS_EMPTY);
    }

    // 通过 HTTP 客户端请求登录接口
    Map<String, String> params = Maps.newHashMap();

    params.put("client_id", SecurityConstants.OAUTH2_CLIENT);
    params.put("client_secret", SecurityConstants.OAUTH2_SECRET);
    params.put("grant_type", OAuth2AccessToken.REFRESH_TOKEN);
    params.put("refresh_token", refreshTokenParam);

    String jsonString = null;
    Map<String, Object> authInfo = null;
    try {
      Response response = OkHttpClientUtils.getInstance().postData(OAUTH_TOKEN_URL, params);
      jsonString = Objects.requireNonNull(response.body()).string();
      authInfo = MapperUtils.json2map(jsonString);
    } catch (Exception e) {
      log.info("调用 /oauth/token refresh_token 失败; {}", e.getMessage());
      throw new BusinessException(ResultCode.LOGIN_SERVER_ERROR);
    }
    if (StringUtils.isEmpty(jsonString) || ObjectUtils.isEmpty(authInfo)) {
      throw new BusinessException(ResultCode.LOGIN_SERVER_ERROR);
    }
    String accessToken = (String) authInfo.get(OAuth2AccessToken.ACCESS_TOKEN);
    if (StringUtils.isEmpty(accessToken)) {
      Integer code = (Integer) authInfo.get("code");
      String message = (String) authInfo.get("message");
      throw new BusinessException(message, code);
    }

    // save redis
    String refreshTokenKey = String.format(RedisKeyConstants.REFRESH_TOKEN_KEY, accessToken);
    String refreshToken = (String) authInfo.get(OAuth2AccessToken.REFRESH_TOKEN);
    redisTemplate.opsForValue().set(refreshTokenKey, refreshToken);

    return accessToken;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override public Boolean updateAccountPhone(String accountId, String phone) {
    return accountMapper.updatePhoneByAccountId(accountId, phone);
  }

  @Override
  public Boolean isLogin() {
    Authentication authentication = (Authentication) this.getAuthentication();
    if (ObjectUtils.isEmpty(authentication)) {
      return false;
    }
    return authentication.getPrincipal() instanceof CustomUserDetails;
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
