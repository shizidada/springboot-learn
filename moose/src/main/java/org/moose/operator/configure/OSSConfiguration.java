package org.moose.operator.configure;

import org.moose.operator.configure.properties.OSSProperties;
import org.moose.operator.util.OSSClientUtils;
import org.moose.operator.util.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-10-14 22:35:22:35
 * @see org.moose.operator.configure
 */
@Configuration
public class OSSConfiguration {

  @Bean OSSClientUtils ossClientUtils(OSSProperties ossProperties, SnowflakeIdWorker snowflakeIdWorker) {
    OSSClientUtils ossClientUtils = new OSSClientUtils();
    ossClientUtils.setOssProperties(ossProperties);
    ossClientUtils.setSnowflakeIdWorker(snowflakeIdWorker);
    return ossClientUtils;
  }
}
