package org.excel.operator.web.security;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.excel.operator.common.api.ResponseResult;
import org.excel.operator.common.api.ResultCode;
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
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

  @Override public void onLogoutSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    log.info(" >>>> CustomLogoutSuccessHandler >>>> 用户 [{}] 登出成功。",
        customUserDetails.getUsername());
    response.setContentType("application/json;charset=UTF-8");
    Integer code = ResultCode.MEMBER_LOGOUT_SUCCESS.getCode();
    String message = ResultCode.MEMBER_LOGOUT_SUCCESS.getMessage();
    ServletOutputStream writer = response.getOutputStream();
    writer.write(JSON.toJSONString(ResponseResult.success(code, message)).getBytes());
    writer.close();
  }
}
