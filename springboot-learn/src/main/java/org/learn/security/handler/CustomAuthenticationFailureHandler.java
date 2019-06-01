package org.learn.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.AjaxResult;
import org.learn.common.api.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录认证失败时返回给前端的数据
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info(" >>>> CustomAuthenticationFailureHandler >>>> 用户登录认证失败");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Long code = ResultCode.MEMBER_PASSWORD_NOT_EXIST.getCode();
        String message = ResultCode.MEMBER_PASSWORD_NOT_EXIST.getMessage();
        response.getWriter().write(JSON.toJSONString(AjaxResult.failure(code, message)));
    }
}
