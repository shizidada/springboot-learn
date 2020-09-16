package org.moose.operator.web.security.granter;

import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.moose.operator.common.api.ResultCode;
import org.moose.operator.constant.DefaultConstants;
import org.moose.operator.constant.RedisKeyConstants;
import org.moose.operator.exception.BusinessException;
import org.moose.operator.model.dto.AccountDTO;
import org.moose.operator.model.dto.RoleDTO;
import org.moose.operator.model.dto.SmsCodeDTO;
import org.moose.operator.web.security.component.CustomUserDetails;
import org.moose.operator.web.service.AccountService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-07-29 22:53:22:53
 * @see org.moose.operator.web.security.granter
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {
  private static final String SMS_GRANT_TYPE = "sms_code";

  private AccountService accountService;

  private RedisTemplate<String, Object> redisTemplate;

  public void setAccountService(AccountService accountService) {
    this.accountService = accountService;
  }

  public void setRedisTemplate(
      RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public SmsCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory) {
    super(tokenServices, clientDetailsService, requestFactory, SMS_GRANT_TYPE);
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
      TokenRequest tokenRequest) {

    Map<String, String> parameters =
        new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

    // 客户端提交的手机号码
    String phoneNumber = parameters.get(DefaultConstants.DEFAULT_PARAMETER_NAME_PHONE);
    if (StringUtils.isBlank(phoneNumber)) {
      throw new BusinessException(ResultCode.PHONE_NUMBER_IS_EMPTY);
    }

    // 客户端提交的验证码
    String smsCode = parameters.get(DefaultConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
    if (StringUtils.isBlank(smsCode)) {
      throw new BusinessException(ResultCode.SMS_CODE_IS_EMPTY);
    }

    //sms_login
    String smsCodeKey =
        String.format(RedisKeyConstants.SMS_CODE_KEY, "sms_login", phoneNumber);
    SmsCodeDTO smsCodeDTO = (SmsCodeDTO) redisTemplate.opsForValue().get(smsCodeKey);

    // 获取服务中保存的用户验证码, 在生成好后放到缓存中
    if (ObjectUtils.isEmpty(smsCodeDTO) || smsCodeDTO.getExpired()) {
      throw new BusinessException(ResultCode.SMS_CODE_ERROR);
    }
    String smsCodeCached = smsCodeDTO.getCode();
    if (!StringUtils.equals(smsCode, smsCodeCached)) {
      throw new BusinessException(ResultCode.SMS_CODE_ERROR);
    }

    // 验证通过后从缓存中移除验证码 etc...
    redisTemplate.expire(smsCodeKey, 0, TimeUnit.SECONDS);

    // 客户端提交的手机号码
    AccountDTO accountDTO = accountService.getAccountByPhone(phoneNumber);

    // TODO: if account not exist , create a new account ??
    if (ObjectUtils.isEmpty(accountDTO)) {
      throw new BusinessException(ResultCode.PHONE_NOT_EXITS);
    }

    // TODO: 权限查询 etc...
    RoleDTO role = new RoleDTO();
    role.setRole("USER");
    //accountService.getAccountRole(accountDTO.getAccountId());

    // 根据手机号码查询用户 ...

    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
    UserDetails user = new CustomUserDetails(accountDTO, null, grantedAuthorities);

    // 验证用户状态(是否禁用 etc...)

    Authentication userAuth =
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    // org.springframework.security.core.userdetails.UserDetails 接口的, 所以有 user.getAuthorities()
    // 当然该参数传null也行
    ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
    OAuth2Request storedOAuth2Request =
        getRequestFactory().createOAuth2Request(client, tokenRequest);
    return new OAuth2Authentication(storedOAuth2Request, userAuth);
  }
}
