package org.learn.security.jwt;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.learn.common.api.AjaxResult;
import org.learn.common.api.ResultCode;
import org.learn.exception.ExceptionModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * 鉴权
 * 用户权限 验证
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        if (!request.getMethod().equals("POST")) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            String message = ResultCode.METHOD_NOT_ALLOWED.getMessage() + request.getMethod();
            response.getWriter().write(JSON.toJSONString(AjaxResult.failure(ResultCode.METHOD_NOT_ALLOWED.getCode(), message)));
            return;
        }

        log.info("JwtAuthorizationFilter doFilterInternal");
        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        // 如果请求头中没有 Authorization 信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有 token，则进行解析，并且设置认证信息
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(tokenHeader);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            // 判断处理异常类型 返回正确提示
            ExceptionModel model = AuthExceptionUtil.processException(ex);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(JSON.toJSONString(AjaxResult.failure(model.getCode(), model.getMessage())));
            return;
        }
        super.doFilterInternal(request, response, chain);
    }

    // 这里从 token 中获取用户信息并新建一个 token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        // 获取 token
        String token = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
        String username = JwtTokenUtil.getUsername(token);
        if (username != null) {
            String userRole = JwtTokenUtil.getUserRole(token);
            return new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singleton(new SimpleGrantedAuthority(userRole))
            );
        }
        return null;
    }
}
