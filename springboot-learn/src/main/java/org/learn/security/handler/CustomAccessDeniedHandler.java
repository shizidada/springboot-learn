package org.learn.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.AjaxResult;
import org.learn.common.api.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权访问
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info(" >>>> CustomAccessDeniedHandler >>>> 用户无权访问");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
        Long code = ResultCode.ACCESS_DENIED.getCode();
        String message = ResultCode.ACCESS_DENIED.getMessage();
        response.getWriter().write(JSON.toJSONString(AjaxResult.failure(code, message)));
    }
}
