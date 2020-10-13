package org.moose.operator.configure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author taohua
 */
//@Configuration
//@ConditionalOnExpression("'${elasticJob.serverList}'.length() > 0")
public class MyJobRegistryCenterConfiguration {

  @Bean(initMethod = "init")
  public ZookeeperRegistryCenter regCenter(
      @Value("${elasticJob.serverList}") final String serverList,
      @Value("${elasticJob.namespace}") final String namespace) {
    return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
  }
}
