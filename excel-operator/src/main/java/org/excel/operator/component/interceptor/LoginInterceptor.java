package org.excel.operator.component.interceptor;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.excel.operator.common.api.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/16 22:43
 * @see org.excel.operator.component.interceptor
 */
public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    HttpSession session = request.getSession();
    if (session.getAttribute("accountName") == null) {
      try {
        this.write(response);
        return false;
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /**
   * 返回未登录
   *
   * @throws IOException
   */
  private void write(ServletResponse servletResponse)
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
