package org.learn.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.learn.security.handler.CustomAccessDeniedHandler;
import org.learn.security.handler.CustomAuthenticationEntryPoint;
import org.learn.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用Security注解，例如最常用的@PreAuthorize
@Slf4j
public class WebSecurityJwtAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        /** 访问资源控制链 */
        // 允许对于网站静态资源的无授权访问
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                ).permitAll()
                // druid 数据库监控
                .antMatchers("/druid/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        // 设置需要权限访问的资源
        httpSecurity.authorizeRequests()
                .antMatchers("/api/v1/test/**").access("hasRole('ROLE_MEMBER')");

        // 对登录注册要允许匿名访问
        httpSecurity.authorizeRequests().antMatchers(
                "/api/v1/member/register",
                "/api/v1/member/login",
                "/api/v1/member/logout",
                "/api/v1/upload/file").permitAll();

        /* web security jwt 拦截鉴权 */
        httpSecurity.addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()));

        httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);

        httpSecurity.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        // 由于使用的是JWT，这里不需要 csrf
        httpSecurity.csrf().disable();

        // 基于 token，所以不需要 session
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 除上面外的所有请求全部需要鉴权认证
        // 测试时全部运行访问
        // .antMatchers("/**") // For Test
        // .permitAll()
        httpSecurity.authorizeRequests().anyRequest().authenticated();

    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }


}
