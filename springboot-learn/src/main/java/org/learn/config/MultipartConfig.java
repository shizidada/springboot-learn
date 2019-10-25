package org.learn.config;

import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartConfig {

  @Value("${upload.maxFileSize}")
  private String uploadMaxFileSize;
  @Value("${upload.MaxRequestSize}")
  private String uploadMaxRequestSize;
  @Value("${upload.tmp}")
  private String uploadTmp;

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();

    factory.setMaxFileSize(2048);
    factory.setMaxRequestSize(10240);
    factory.setLocation(uploadTmp);
    return factory.createMultipartConfig();
  }

  @Bean
  public CommonsMultipartResolver multipartResolver() {
    return new CommonsMultipartResolver();
  }
}
}
