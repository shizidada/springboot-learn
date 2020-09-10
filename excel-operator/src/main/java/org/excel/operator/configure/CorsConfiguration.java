package org.excel.operator.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-05-21 13:29:13:29
 * @see org.excel.operator.configure
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
  @Override public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
        .allowCredentials(true)
        .maxAge(3600)
        .allowedHeaders("*");
  }

  ///**
  // * 设置跨域
  // *
  // * @return CorsConfigurationSource
  // */
  //@Bean
  //CorsConfigurationSource corsConfigurationSource() {
  //  org.springframework.web.cors.CorsConfiguration
  //      configuration = new org.springframework.web.cors.CorsConfiguration();
  //  configuration.setAllowedOrigins(Collections.singletonList("*"));
  //  configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
  //  configuration.setAllowCredentials(Boolean.TRUE);
  //  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  //  source.registerCorsConfiguration("/**", configuration);
  //  return source;
  //}
}
