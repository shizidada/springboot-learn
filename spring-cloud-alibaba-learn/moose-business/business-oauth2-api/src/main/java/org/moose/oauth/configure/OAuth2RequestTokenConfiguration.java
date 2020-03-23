package org.moose.oauth.configure;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.moose.oauth.interceptor.OAuth2RequestTokenInterceptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Description
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020 2020/3/22 09:58
 * @see org.moose.oauth.configure
 */
@Configuration
public class OAuth2RequestTokenConfiguration {

  @Bean
  OAuth2RequestTokenInterceptor oAuth2FeignRequestInterceptor() {
    return new OAuth2RequestTokenInterceptor();
  }

  /**
   * form 编码器，实现支持 form 表单提交
   */
  @Bean
  public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
    return new FormEncoder(new SpringEncoder(messageConverters));
  }

  @Bean
  public Logger.Level logger() {
    return Logger.Level.FULL;
  }
}
