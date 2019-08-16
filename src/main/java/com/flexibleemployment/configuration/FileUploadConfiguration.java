package com.flexibleemployment.configuration;

import com.flexibleemployment.configuration.properties.FileUploadProperties;
import com.flexibleemployment.utils.file.FileUploadHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({FileUploadProperties.class})
public class FileUploadConfiguration {


    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Bean
    public FileUploadHandler fileUploadHandler() {
        return new FileUploadHandler(fileUploadProperties);
    }

}
