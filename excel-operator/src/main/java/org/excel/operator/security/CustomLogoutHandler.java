package org.excel.operator.security;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.excel.operator.common.api.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
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
public class CustomLogoutHandler implements LogoutHandler {

  private Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class);

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    logger.info(" >>>> CustomLogoutHandler >>>> 用户 [{}] 登出失败。", authentication.getDetails());
  }
}
