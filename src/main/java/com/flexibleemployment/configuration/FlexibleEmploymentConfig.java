package com.flexibleemployment.configuration;

import com.flexibleemployment.configuration.properties.SnowflakeProperties;
import com.flexibleemployment.utils.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlexibleEmploymentConfig {

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(SnowflakeProperties properties) {
        return new SnowflakeIdWorker(properties.getWorkerId(), properties.getDataCenterId());
    }
}
