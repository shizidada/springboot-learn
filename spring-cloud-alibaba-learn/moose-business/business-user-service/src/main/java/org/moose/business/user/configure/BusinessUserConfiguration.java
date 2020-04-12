package org.moose.business.user.configure;

import org.moose.commons.base.snowflake.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * <p>
 * Description:
 * </p>
 *
 * @author taohua
 * @version v1.0.0
 * @date 2020-03-31 13:31:13:31
 * @see org.moose.business.user.configure
 */
@Configuration
public class BusinessUserConfiguration {

  @Bean
  SnowflakeIdWorker snowflakeIdWorker() {
    return new SnowflakeIdWorker(1, 1);
  }

  //@Resource
  //private DataSourceProperties dataSourceProperties;
  //
  //@Bean
  //@Primary
  //public DruidDataSource druidDataSource() {
  //  DruidDataSource druidDataSource = new DruidDataSource();
  //  druidDataSource.setUrl(dataSourceProperties.getUrl());
  //  druidDataSource.setUsername(dataSourceProperties.getUsername());
  //  druidDataSource.setPassword(dataSourceProperties.getPassword());
  //  druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
  //  druidDataSource.setInitialSize(0);
  //  druidDataSource.setMaxActive(180);
  //  druidDataSource.setMaxWait(60000);
  //  druidDataSource.setMinIdle(0);
  //  druidDataSource.setValidationQuery("Select 1 from DUAL");
  //  druidDataSource.setTestOnBorrow(false);
  //  druidDataSource.setTestOnReturn(false);
  //  druidDataSource.setTestWhileIdle(true);
  //  druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
  //  druidDataSource.setMinEvictableIdleTimeMillis(25200000);
  //  druidDataSource.setRemoveAbandoned(true);
  //  druidDataSource.setRemoveAbandonedTimeout(1800);
  //  druidDataSource.setLogAbandoned(true);
  //  return druidDataSource;
  //}
  //
  //@Primary
  //@Bean
  //public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource) {
  //  return new DataSourceProxy(druidDataSource);
  //}
}
