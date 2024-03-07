//package com.sparta.homework3.config;
//
//
//
//import jdk.javadoc.doclet.Doclet;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * Swagger springdoc-ui 구성 파일
// */
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public Doclet api() {
//        return new Docket(DocumentationType.OAS_30) // open api spec 3.0
//                .groupName("groupName")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.swagger"))
//                .paths(PathSelectors.ant("/api/**"))
//                //.apis(RequestHandlerSelectors.any())
//                //.paths(PathSelectors.any())
//                .build()
//                .apiInfo(this.apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Hello Swagger")
//                .description("스웨거 기능 테스트")
//                .version("1.0")
//                .build();
//    }
//}