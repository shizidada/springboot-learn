//package org.learn.config;
//
//import com.google.common.base.Predicates;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.core.env.Environment;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@Profile({"dev", "test"})
//@EnableSwagger2
//public class Swagger2Config {
//
//    @Autowired
//    Environment environment;
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(environment.getProperty("spring.profiles.active").equals("dev"))
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("org.learn.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("SpringBoot 利用 Swagger 构建 api 文档")
//                .description("简单优雅的 RESTful 风格，http://github.com/shizidada")
//                .termsOfServiceUrl("http://github.com/shizidada")
//                .version("1.0.0")
//                .build();
//    }
//}
