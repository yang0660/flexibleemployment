package com.flexibleemployment.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wxhao
 * @date 2018/3/15
 */
@Data
@Component
@ConfigurationProperties(prefix = "snowflake")
public class SnowflakeProperties {

    /**
     * 数据中心ID
     */
    private long dataCenterId = 0L;

    /**
     * 工作ID
     */
    private long workerId = 0L;


}
