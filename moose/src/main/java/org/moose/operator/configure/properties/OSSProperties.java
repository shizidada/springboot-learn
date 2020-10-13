package org.moose.operator.configure.properties;

import java.io.Serializable;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author taohua
 */
@Data
@Configuration
public class OSSProperties implements Serializable {

  @Value("${aliyun.oss.endpoint}")
  private String endpoint;

  @Value("${aliyun.oss.accessKeyId}")
  private String accessKeyId;

  @Value("${aliyun.oss.accessKeySecret}")
  private String accessKeySecret;

  @Value("${aliyun.oss.bucketName}")
  private String bucketName;
}
