package com.flexibleemployment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by ace on 2017/9/22.
 */
@Configuration
@EnableSwagger2
@Profile({"default", "dev-online", "test"})
public class SwaggerConfiguration {

    @Bean
    public Docket appDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(appApiInfo())
                .directModelSubstitute(Date.class, Double.class)
                .directModelSubstitute(LocalDate.class, Double.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.flexibleemployment.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo appApiInfo() {
        return new ApiInfoBuilder()
                .title("flexibleemployment-web 接口文档")
                .version("1.0.0")
                .build();
    }

}
