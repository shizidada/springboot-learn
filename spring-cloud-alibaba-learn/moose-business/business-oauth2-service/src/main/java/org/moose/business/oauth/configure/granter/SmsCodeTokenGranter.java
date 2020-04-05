package org.moose.business.oauth.configure.granter;

import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.moose.business.oauth.model.dto.OAuth2UserDetails;
import org.moose.business.oauth.service.OAuth2Service;
import org.moose.commons.base.code.PhoneCode;
import org.moose.commons.base.code.SmsCode;
import org.moose.commons.base.exception.BusinessException;
import org.moose.provider.account.model.dto.AccountDTO;
import org.moose.provider.account.model.dto.RoleDTO;
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
 * @date 2020-03-30 17:50:17:50
 * @see org.moose.business.oauth.configure.granter
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {
  private static final String GRANT_TYPE = "sms_code";

  private OAuth2Service oAuth2Service;

  public void setoAuth2Service(OAuth2Service oAuth2Service) {
    this.oAuth2Service = oAuth2Service;
  }

  public SmsCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory) {
    super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
      TokenRequest tokenRequest) {

    Map<String, String> parameters =
        new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

    // 验证验证码

    // 客户端提交的验证码
    String smsCode = parameters.get("smsCode");
    if (StringUtils.isBlank(smsCode)) {
      throw new BusinessException(SmsCode.SMS_CODE_MUST_NOT_BE_NULL.getCode(),
          SmsCode.SMS_CODE_MUST_NOT_BE_NULL.getMessage());
    }

    // 获取服务中保存的用户验证码, 在生成好后放到缓存中
    String smsCodeCached = "123456";
    if (StringUtils.isBlank(smsCodeCached)) {
      throw new BusinessException(
          SmsCode.SMS_CODE_NOT_FOUNT.getCode(), SmsCode.SMS_CODE_NOT_FOUNT.getMessage());
    }
    if (!smsCode.equals(smsCodeCached)) {
      throw new BusinessException(
          SmsCode.SMS_CODE_ERROR.getCode(), SmsCode.SMS_CODE_ERROR.getMessage());
    }

    // 验证通过后从缓存中移除验证码 etc...

    // 客户端提交的手机号码
    String phone = parameters.get("phone");
    AccountDTO accountDTO = oAuth2Service.getAccountByPhone(phone);

    // TODO if account not exist , create a new account ??
    if (accountDTO == null) {
      throw new BusinessException(
          PhoneCode.PHONE_NOT_FOUND.getCode(), PhoneCode.PHONE_NOT_FOUND.getMessage());
    }

    RoleDTO role = oAuth2Service.getAccountRole(accountDTO.getAccountId());

    // 根据手机号码查询用户 ...
    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
    UserDetails user = new OAuth2UserDetails(accountDTO, null, grantedAuthorities);

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
