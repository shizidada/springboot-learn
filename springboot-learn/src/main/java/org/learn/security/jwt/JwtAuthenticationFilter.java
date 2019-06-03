package org.learn.security.jwt;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.learn.common.Constants;
import org.learn.common.api.AjaxResult;
import org.learn.common.api.ResultCode;
import org.learn.exception.ExceptionModel;
import org.learn.utils.JwtTokenUtil;
import org.learn.security.CustomUserDetails;
import org.learn.service.model.MemberModel;
import org.learn.utils.AuthExceptionUtil;
import org.learn.utils.RedisUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证
 * 用户认证 验证
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    private static final String LOGIN_URL = "/api/v1/member/login";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl(LOGIN_URL);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // TODO ?
        String url = request.getRequestURL().toString();
        if (!request.getMethod().equals("POST") && url.contains(LOGIN_URL)) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        log.info("contentType :: {} ", request.getContentType());
        // username password  验证
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isAnyBlank(username, password)) {
            throw new AuthenticationServiceException(ResultCode.MEMBER_PASSWORD_NOT_EXIST.getMessage());
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password, authorities)
        );
    }

    /**
     * 成功验证后调用的方法
     * 如果验证成功，就生成 token 并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        // 查看源代码会发现调用 getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是 CustomUserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        MemberModel memberModel = customUserDetails.getMemberModel();
        log.info("用户 :: [{}] :: 登录成功", memberModel.getUsername());
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        String role = "";
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        String token = JwtTokenUtil.createToken(customUserDetails.getUsername(), role, false);
        // 返回创建成功 token 但是这里创建 token 只是单纯 token
        // 按照 jwt 规定，最后请求的格式应该是 `Bearer token`
        // response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
        // redisUtil.set(token, customUserDetails.getMemberModel().getId());
        Map<String, Object> map = new HashMap<>();
        map.put("member_info", memberModel);
        map.put("access_token", Constants.JWT.TOKEN_PREFIX + token);
        response.getWriter().write(JSON.toJSONString(AjaxResult.success("登录成功", map)));
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException ex) throws IOException, ServletException {
        log.info("验证失败 :: {} ", ex.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ExceptionModel model = AuthExceptionUtil.processException(ex);
        response.getWriter().write(JSON.toJSONString(AjaxResult.failure(model.getCode(), model.getMessage())));
    }
}
