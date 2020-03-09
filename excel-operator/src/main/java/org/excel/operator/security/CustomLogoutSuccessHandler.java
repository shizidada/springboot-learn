package org.excel.operator.security;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.common.api.ResultCode;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2019 2019/11/20 21:57
 * @see org.excel.operator.component
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

  private Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

  @Override public void onLogoutSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getDetails();

    logger.info(" >>>> CustomLogoutSuccessHandler >>>> 用户 [{}] 登出成功。",
        customUserDetails.getUsername());

    response.setContentType("application/json;charset=UTF-8");
    Integer code = ResultCode.MEMBER_LOGOUT_SUCCESS.getCode();
    String message = ResultCode.MEMBER_LOGOUT_SUCCESS.getMessage();
    ServletOutputStream writer = response.getOutputStream();
    writer.write(JSON.toJSONString(ResponseResult.fail(code, message)).getBytes());
    writer.close();
  }
}
