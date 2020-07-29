package org.excel.operator.web.security.granter;

import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.excel.operator.common.api.ResultCode;
import org.excel.operator.exception.BusinessException;
import org.excel.operator.model.dto.AccountDTO;
import org.excel.operator.model.dto.RoleDTO;
import org.excel.operator.web.security.component.CustomUserDetails;
import org.excel.operator.web.service.AccountService;
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
 * @see org.excel.operator.web.security.granter
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {
  private static final String GRANT_TYPE = "sms_code";

  private AccountService accountService;

  public void setAccountService(AccountService accountService) {
    this.accountService = accountService;
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
      throw new BusinessException(ResultCode.SMS_CODE_IS_EMPTY);
    }

    // 获取服务中保存的用户验证码, 在生成好后放到缓存中
    String smsCodeCached = "123456";
    if (StringUtils.isBlank(smsCodeCached)) {
      throw new BusinessException(ResultCode.SMS_CODE_NOT_EXITS);
    }
    if (!smsCode.equals(smsCodeCached)) {
      throw new BusinessException(ResultCode.SMS_CODE_ERROR);
    }

    // 验证通过后从缓存中移除验证码 etc...

    // 客户端提交的手机号码
    String phone = parameters.get("phone");
    AccountDTO accountDTO = accountService.getAccountByPhone(phone);

    // TODO if account not exist , create a new account ??
    if (accountDTO == null) {
      throw new BusinessException(ResultCode.PHONE_NOT_EXITS);
    }

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
