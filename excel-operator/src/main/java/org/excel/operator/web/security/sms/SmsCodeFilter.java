package org.excel.operator.web.security.sms;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.excel.operator.exception.BusinessException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-06-15 22:04:22:04
 * @see org.excel.operator.web.security.sms
 */
public class SmsCodeFilter extends OncePerRequestFilter {

  private static final String URL = "/authentication/mobile";

  private static final String METHOD = "post";

  private AuthenticationFailureHandler authenticationFailureHandler;

  public AuthenticationFailureHandler getAuthenticationFailureHandler() {
    return authenticationFailureHandler;
  }

  public void setAuthenticationFailureHandler(
      AuthenticationFailureHandler authenticationFailureHandler) {
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.equals(URL) && request.getMethod().equals(METHOD)) {
      try {
        // new ServletWebRequest(request)
        validate(request);
      } catch (BusinessException e) {
        authenticationFailureHandler.onAuthenticationFailure(request,
            response, e);
        return;
      }
    }

    /**
     * 如果不是登录请求，直接调用后面的过滤器链
     */
    filterChain.doFilter(request, response);
  }

  private void validate(HttpServletRequest request) {
    HttpSession session = request.getSession();
    ValidateCode validateCode = (ValidateCode) session.getAttribute("SESSION_KEY");
    String smsCode = request.getParameter("smsCode");
    if (!StringUtils.hasText(smsCode)) {
      throw new BusinessException("验证码的值不能为空");
    }
    if (validateCode == null) {
      throw new BusinessException("验证码不存在");
    }
    if (validateCode.isExpried()) {
      session.removeAttribute("SESSION_KEY");
      throw new BusinessException("验证码已过期");
    }
    if (!validateCode.getCode().equals(smsCode)) {
      throw new BusinessException("验证码不正确");
    }
    session.removeAttribute("SESSION_KEY");
  }
}
