package com.flexibleemployment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.flexibleemployment.dao.mapper"})
public class FlexibleEmploymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlexibleEmploymentApplication.class, args);
    }
}
