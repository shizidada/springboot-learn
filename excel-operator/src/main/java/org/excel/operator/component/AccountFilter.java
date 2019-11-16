package org.excel.operator.component;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.excel.operator.common.api.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * @author taohua
 */
public class AccountFilter implements Filter {
  private static Logger logger = LoggerFactory.getLogger(AccountFilter.class);

  @Override public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpSession session = request.getSession();
    if (session.getAttribute("username") == null) {
      this.write(servletRequest, servletResponse);
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override public void destroy() {
  }

  /**
   * 返回未登录
   *
   * @param servletRequest
   * @param servletResponse
   * @throws IOException
   */
  private void write(ServletRequest servletRequest, ServletResponse servletResponse)
      throws IOException {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setHeader("Content-Type", "application/json");
    ServletOutputStream out = response.getOutputStream();
    ResponseResult<Object> result = new ResponseResult<>();
    result.setCode(HttpStatus.UNAUTHORIZED.value());
    result.setStatus(Boolean.FALSE);
    result.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    out.print(JSON.toJSONString(result));
    out.flush();
    out.close();
  }
}
