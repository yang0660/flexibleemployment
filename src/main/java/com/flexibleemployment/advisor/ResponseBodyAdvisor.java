package com.flexibleemployment.advisor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by zhangwanli on 2017/8/25.
 */
public class ResponseBodyAdvisor implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseBodyAdvisor.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType)
                || returnType.getMethod().isAnnotationPresent(ResponseBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (logger.isInfoEnabled()) {
            if (body != null) {
                String bodyStr = JSON.toJSONString(body, SerializerFeature.UseSingleQuotes);
                if (bodyStr.getBytes().length < 1024 * 500) {
                    logger.info("{}: {}", request.getURI(), JSON.toJSONString(body, SerializerFeature.UseSingleQuotes));
                    //            logger.info("json response=========>url:{}", request.getURI());
                }
            }
        }
        return body;
    }

}
