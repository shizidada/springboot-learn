package org.learn.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用Security注解，例如最常用的@PreAuthorize
@Slf4j
public class WebSecurityAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
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

        httpSecurity.httpBasic().authenticationEntryPoint(customAuthenticationEntryPoint);
        // 无权访问 JSON 格式的数据
        httpSecurity.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        // 自定义登录成功
        httpSecurity
                .formLogin()
                .loginProcessingUrl("/api/v1/member/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler); // 登录失败

        // 自定义登出成功 TODO 这个地方不知道怎么？？？
        httpSecurity.logout()
                // 自定义 url
                .logoutUrl("/api/v1/member/logout")
                // 成功退出之后重定向 url
                // .logoutSuccessUrl("")
                // 自定义登出成功返回
                .logoutSuccessHandler(customLogoutSuccessHandler)
                // 自定义登出成功
                .addLogoutHandler(customLogoutHandler)
                // 清理 Session
                .invalidateHttpSession(true);

        // 由于使用的是JWT，这里不需要 csrf
        httpSecurity.csrf().disable();

        // 基于token，所以不需要 session TODO will use jwt
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 除上面外的所有请求全部需要鉴权认证
        // 测试时全部运行访问
        // .antMatchers("/**") // For Test
        // .permitAll()
        httpSecurity.authorizeRequests().anyRequest().authenticated();

    }

    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return new CorsFilter(source);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        log.info("加密密码 :: {}", encode);
        boolean matches = bCryptPasswordEncoder.matches("123456", encode);
        log.info(" 是否相等 :: {} ", matches);
    }
}