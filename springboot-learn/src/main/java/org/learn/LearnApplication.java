package org.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 启注解事务管理，等同于 xml 配置方式的 <tx:annotation-driven>
// @EnableTransactionManagement
// 没有添加 @Mapper 注解通过这种方式进行扫描
// @MapperScan("org.learn.mapper")
//@ServletComponentScan // 扫描 servlet filter listener
@SpringBootApplication
public class LearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnApplication.class, args);
    }

//    @Bean
//    public Object testGetDefaultManager(PlatformTransactionManager platformTransactionManager) {
//        System.out.println("What Manager? ----> " + platformTransactionManager.getClass().getName());
//        return new Object();
//    }
}
