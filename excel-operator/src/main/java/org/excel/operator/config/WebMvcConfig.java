package org.excel.operator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author taohua
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  String[] addPathPatterns = {"/**"};

  String[] excludePathPatterns = {"/", "/api/v1/account/login"};

  @Override public void addInterceptors(InterceptorRegistry registry) {
    //registry.addInterceptor(new LoginInterceptor())
    //    .addPathPatterns(addPathPatterns)
    //    .excludePathPatterns(excludePathPatterns);
  }
}
