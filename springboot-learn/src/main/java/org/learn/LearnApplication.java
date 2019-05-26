package org.learn;

import org.learn.common.SnowflakeIdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


// 启注解事务管理，等同于 xml 配置方式的 <tx:annotation-driven>
@EnableTransactionManagement
@SpringBootApplication
// 没有添加 @Mapper 注解通过这种方式进行扫描
@MapperScan("org.learn.mapper")
// @ServletComponentScan // 扫描 servlet filter listener
public class LearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnApplication.class, args);
    }

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(0, 0);
    }
}
