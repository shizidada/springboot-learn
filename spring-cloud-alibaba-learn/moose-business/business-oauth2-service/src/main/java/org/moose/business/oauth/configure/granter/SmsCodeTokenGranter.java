package org.moose.business.oauth.configure.granter;

import com.google.common.collect.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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

    // 客户端提交的用户名
    String mobile = parameters.get("mobile");

    // 客户端提交的验证码
    String smsCode = parameters.get("smsCode");

    // 根据手机号码查询用户 ...
    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    UserDetails user = new User("tom", "N/A", grantedAuthorities);
    if (user == null) {
      throw new InvalidGrantException("用户不存在");
    }

    //验证用户状态(是否禁用 etc...)

    // 验证验证码
    // 获取服务中保存的用户验证码, 在生成好后放到缓存中
    String smsCodeCached = "123456";
    if (StringUtils.isBlank(smsCodeCached)) {
      throw new InvalidGrantException("用户没有发送验证码");
    }
    if (!smsCode.equals(smsCodeCached)) {
      throw new InvalidGrantException("验证码不正确");
    }
    // 验证通过后从缓存中移除验证码 etc...

    Authentication userAuth =
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    // org.springframework.security.core.userdetails.UserDetails 接口的, 所以有 user.getAuthorities()
    // 当然该参数传null也行
    ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
    OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
    return new OAuth2Authentication(storedOAuth2Request, userAuth);
  }
}
