package org.moose.business.oauth.configure.sms;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-30 14:55:14:55
 * @see org.moose.business.oauth.configure.sms
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  public static final String MOOSE_MOBILE_CODE_METHOD = "POST";

  public static final String MOOSE_MOBILE_CODE_KEY = "mobile";
  private String mobileParameter = MOOSE_MOBILE_CODE_KEY;

  private boolean postOnly = true;

  // ~ Constructors
  // ===================================================================================================

  protected SmsCodeAuthenticationFilter() {
    super(new AntPathRequestMatcher("/oauth/mobile", MOOSE_MOBILE_CODE_METHOD));
  }

  // ~ Methods
  // ========================================================================================================

  @Override public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    if (postOnly && !request.getMethod().equals(MOOSE_MOBILE_CODE_METHOD)) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    }

    String mobile = obtainMobile(request);
    if (mobile == null) {
      mobile = "";
    }

    mobile = mobile.trim();

    SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

    // Allow subclasses to set the "details" property
    setDetails(request, authRequest);

    return this.getAuthenticationManager().authenticate(authRequest);
  }

  protected void setDetails(HttpServletRequest request,
      SmsCodeAuthenticationToken authRequest) {
    authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
  }

  @Nullable
  protected String obtainMobile(HttpServletRequest request) {
    return request.getParameter(mobileParameter);
  }

  public void setPostOnly(boolean postOnly) {
    this.postOnly = postOnly;
  }

  public void setMobileParameter(String mobileParameter) {
    Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
    this.mobileParameter = mobileParameter;
  }

  public final String getMobileParameter() {
    return mobileParameter;
  }
}
