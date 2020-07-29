package org.excel.operator.web.security.configure;

import org.excel.operator.constants.SecurityConstants;
import org.excel.operator.web.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    //这里client使用存在模式，可以实际过程调整为jdbc的方式
    //这里说明一下，redirectUris的连接可以是多个，这里通过access_token都可以访问的
    //简单点，就是授权的过程
    clients.inMemory()
        .withClient(SecurityConstants.OAUTH2_CLIENT)
        .secret(passwordEncoder.encode(SecurityConstants.OAUTH2_SECRET))
        .authorizedGrantTypes("authorization_code", "refresh_token")
        .scopes("All")
        .autoApprove(true)
        .redirectUris("http://moose.com:7000/login");
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    //权限控制
    security.tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        .allowFormAuthenticationForClients();
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    //认证体系使用security的方式
    endpoints.authenticationManager(authenticationManager);

    //允许调用方式
    endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

    endpoints.userDetailsService(userDetailsService);
  }
}
