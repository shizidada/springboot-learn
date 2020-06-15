package org.excel.operator.web.security.sms;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * @date 2020-06-15 23:13:23:13
 * @see org.excel.operator.web.security.sms
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  public static final String CORE_FORM_MOBILE_KEY = "mobile";

  private static final String MOBILE_URL = "/authentication/mobile";

  private static final String REQUEST_METHOD = "POST";
  /**
   * 请求中携带手机号的参数名称
   */
  private String mobileParameter = CORE_FORM_MOBILE_KEY;

  /**
   * 指定当前过滤器是否只处理POST请求
   */
  private boolean postOnly = true;

  protected SmsCodeAuthenticationFilter() {
    super(new AntPathRequestMatcher(MOBILE_URL, REQUEST_METHOD));
  }

  @Override public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    if (this.postOnly && !request.getMethod().equals(REQUEST_METHOD)) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    } else {
      String mobile = this.obtainMobile(request);
      if (mobile == null) {
        mobile = "";
      }
      mobile = mobile.trim();
      SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
      this.setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
    }
  }

  /**
   * 获取手机号码
   *
   * @param request
   * @return
   */
  protected String obtainMobile(HttpServletRequest request) {
    return request.getParameter(this.mobileParameter);
  }

  /**
   * 把请求的详情，例如请求ip、SessionId等设置到验证请求中去
   *
   * @param request
   * @param authRequest
   */
  protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
  }

  public void setPostOnly(boolean postOnly) {
    this.postOnly = postOnly;
  }

  public final String getMobileParameter() {
    return this.mobileParameter;
  }

  public void setMobileParameter(String mobileParameter) {
    Assert.hasText(mobileParameter, "mobile parameter must not be empty or null");
    this.mobileParameter = mobileParameter;
  }
}
