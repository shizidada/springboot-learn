package org.moose.operator.configure;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author taohua
 */
@Configuration
public class ElasticsearchConfiguration {

  @Bean
  public RequestOptions defaultRequestOptions() {
    RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
    // 添加所有请求所需的任何标头。
    // builder.addHeader("Authorization", "Bearer " + TOKEN);
    // 自定义响应使用者
    //builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
    return builder.build();
  }

  @Bean
  public RestHighLevelClient client() {
    return new RestHighLevelClient(
        RestClient.builder(
            new HttpHost("localhost", 9200, "http")));
  }
}
