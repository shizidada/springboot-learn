package org.excel.operator.web.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.annotation.Resource;
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

  @Resource private ObjectMapper objectMapper;

  @Override public void onLogoutSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    response.setContentType("application/json;charset=UTF-8");
    ServletOutputStream writer = response.getOutputStream();

    if (authentication == null) {
      objectMapper.writeValue(writer, new ResponseResult<String>(ResultCode.NOT_LOGIN, null));
      writer.close();
      return;
    }

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    log.info("CustomLogoutSuccessHandler 用户 [{}] 登出成功。", userDetails.getUsername());

    Integer code = ResultCode.LOGOUT_SUCCESS.getCode();
    String message = ResultCode.LOGOUT_SUCCESS.getMessage();
    objectMapper.writeValue(writer, new ResponseResult<>(code, message));
    writer.close();
  }
}
