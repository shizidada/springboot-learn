package org.excel.operator.config;

import org.excel.operator.component.AccountFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean<AccountFilter> testFilterRegistration() {
    FilterRegistrationBean<AccountFilter> registration = new FilterRegistrationBean<AccountFilter>();
    registration.setFilter(new AccountFilter());
    registration.addUrlPatterns("/api/*");
    registration.setOrder(1);
    return registration;
  }
}
