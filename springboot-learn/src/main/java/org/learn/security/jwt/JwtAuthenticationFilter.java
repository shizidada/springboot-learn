package org.learn.security.jwt;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.learn.common.api.AjaxResult;
import org.learn.security.CustomUserDetails;
import org.learn.utils.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
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

/**
 * 认证
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            CustomUserDetails customUserDetails = new ObjectMapper().readValue(request.getInputStream(), CustomUserDetails.class);
            // username password  验证
            String username = customUserDetails.getUsername();
            String password = customUserDetails.getPassword();
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password, authorities)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 查看源代码会发现调用 getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是 CustomUserDetails 啦
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        System.out.println("jwtUser:" + customUserDetails.toString());
        String token = JwtTokenUtil.createToken(customUserDetails.getUsername(), false);
        // 返回创建成功的token
        // 但是这里创建的 token 只是单纯的 token
        // 按照 jwt 的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String message = failed.getMessage();
        response.getWriter().write(JSON.toJSONString(AjaxResult.failure(message)));
    }
}
