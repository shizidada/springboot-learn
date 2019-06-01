package org.learn.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.AjaxResult;
import org.learn.common.api.ResultCode;
import org.learn.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功
 */
@Component
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("CustomLogoutSuccessHandler 登出成功");
        // TODO ?? 没有 authentication 信息
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getDetails();
        response.setContentType("application/json;charset=UTF-8");
        Long code = ResultCode.MEMBER_LOGOUT_SUCCESS.getCode();
        String message = ResultCode.MEMBER_LOGOUT_SUCCESS.getMessage();
        response.getWriter().write(JSON.toJSONString(AjaxResult.failure(code, message)));
    }
}
