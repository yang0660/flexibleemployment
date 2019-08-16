package com.flexibleemployment.configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by zhangwanli on 2017/4/12.
 */
@Configuration
@EnableSwagger2
public class WebStarterConfiguration {

    /*@Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding("utf-8");
        filter.setForceRequestEncoding(true);
        filter.setForceResponseEncoding(true);
        return filter;
    }*/


    @Controller
    @Api(hidden = true)
    public class IndexController implements ApplicationListener<ApplicationReadyEvent> {

        private String applicationName = "application";

        @ApiOperation(value = "首页", hidden = true)
        @RequestMapping("/")
        public String index(Model model) {
            model.addAttribute("applicationName", applicationName);
            return "index";
        }

        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            SpringApplication springApplication = event.getSpringApplication();
            Class<?> mainApplicationClass = springApplication.getMainApplicationClass();
            applicationName = mainApplicationClass.getSimpleName();
        }

    }

}


