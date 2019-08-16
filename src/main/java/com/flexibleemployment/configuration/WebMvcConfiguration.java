package com.flexibleemployment.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.flexibleemployment.advisor.RequestBodyAdvisor;
import com.flexibleemployment.advisor.ResponseBodyAdvisor;
import com.flexibleemployment.configuration.properties.FileUploadProperties;
import com.flexibleemployment.helper.BeanFactoryHelper;
import com.flexibleemployment.interceptor.PermissionInterceptorAdapter;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Configuration
@EnableConfigurationProperties({JacksonProperties.class, FileUploadProperties.class})

public class WebMvcConfiguration extends DelegatingWebMvcConfiguration {

    @Autowired
    private JacksonProperties jacksonProperties;

    @Autowired
    private FileUploadProperties fileUploadProperties;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"
    };

    private List<String> excludeAuthPaths = Lists.newArrayList(
            "/favicon.ico",
            "/error",
            "/doc.html",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/webjars/**",
            "/static/**",
            "/public/**",
            "/templates/**",
            "/file-upload/**"
    );

    @Override
    @Autowired(required = false)
    public void setConfigurers(List<WebMvcConfigurer> configurers) {
        if (configurers == null) {
            configurers = new ArrayList<>();
        }
        configurers.add(webMvcConfigurer(fileUploadProperties));
        super.setConfigurers(configurers);
    }

    @Bean
    public PermissionInterceptorAdapter getSecurityInterceptor() {
        PermissionInterceptorAdapter interceptorAdapter = new PermissionInterceptorAdapter();
        interceptorAdapter.setExcludeAuthPaths(excludeAuthPaths);
        return interceptorAdapter;
    }


    @Bean
    public WebMvcConfigurer webMvcConfigurer(FileUploadProperties fileUploadProperties) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(getSecurityInterceptor());

                BeanFactory beanFactory = BeanFactoryHelper.getBeanFactory();
                List<HandlerInterceptor> interceptors = new ArrayList<>();
                if (beanFactory instanceof DefaultListableBeanFactory) {
                    Collection<HandlerInterceptor> values = ((DefaultListableBeanFactory) beanFactory).getBeansOfType(HandlerInterceptor.class).values();
                    interceptors.addAll(values);
                }

                for (HandlerInterceptor interceptor : interceptors) {
                    registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/error");
                }
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                if (!registry.hasMappingForPattern("/webjars/**")) {
                    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/", "classpath:/webjars/");
                }
                if (!registry.hasMappingForPattern("/**")) {
                    registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
                }
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
                registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
                // 文件上传路径
                registry.addResourceHandler(fileUploadProperties.getFileFolder() + "/**")
                        .addResourceLocations("file:" + fileUploadProperties.getBasePath() + "/" + fileUploadProperties.getFileFolder() + "/");

                // 文件上传路径
            }

            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new StringDateConvert());
            }

            /*@Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("*//**")
             .allowedOrigins("*")
             .allowedHeaders("*")
             .allowedMethods("*")
             .allowCredentials(true);
             }*/
        };
    }

    private CorsConfiguration addcorsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("http://zzz.vcforest.cn");
        list.add("*");
        corsConfiguration.setAllowedOrigins(list);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", addcorsConfig());
        return new CorsFilter(source);
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        if (jacksonProperties.getDefaultPropertyInclusion() == null) {
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        }
        /*builder.serializerByType(Long.class, new StdScalarSerializer<Long>(Long.class) {
            private static final long serialVersionUID = 4509301735278156105L;
            private static final long JS_INTEGER_MAX_PRECISION_VALUE = 999999999999999L;

            @Override
            public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                if (value > JS_INTEGER_MAX_PRECISION_VALUE) {
                    gen.writeString(value.toString());
                } else {
                    gen.writeNumber(value);
                }
            }
        });*/
        builder.serializerByType(Long.class, ToStringSerializer.instance);
        builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
        builder.serializerByType(BigDecimal.class, new StdScalarSerializer<BigDecimal>(BigDecimal.class) {
            private static final long serialVersionUID = 8876113622852558483L;

            @Override
            public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                if (null != value && value.scale() < 2) {
                    DecimalFormat df = new DecimalFormat("0.00");
                    gen.writeString(df.format(value));
                } else {
                    gen.writeString(String.valueOf(value));
                }
            }
        });
        builder.deserializerByType(Date.class, new StdScalarDeserializer<Date>(Date.class) {
            private static final long serialVersionUID = -1881315730504643020L;

            private StringDateConvert convert = new StringDateConvert();

            @Override
            public Date deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
                JsonToken t = p.getCurrentToken();
                if (t == JsonToken.VALUE_NUMBER_INT) {
                    return new Date(p.getLongValue());
                }
                if (t == JsonToken.VALUE_NULL) {
                    return getNullValue(ctx);
                }
                if (t == JsonToken.VALUE_STRING) {
                    Date date = this.convert.convert(p.getText());
                    if (date == null) {
                        date = _parseDate(p.getText().trim(), ctx);
                    }
                    return date;
                }
                // [databind#381]
                if (t == JsonToken.START_ARRAY && ctx.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                    p.nextToken();
                    final Date parsed = _parseDate(p, ctx);
                    t = p.nextToken();
                    if (t != JsonToken.END_ARRAY) {
                        handleMissingEndArrayForSingle(p, ctx);
                    }
                    return parsed;
                }
                return (Date) ctx.handleUnexpectedToken(_valueClass, p);
            }
        });

        return builder.createXmlMapper(false).build();
    }

    @Override
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        adapter.setRequestBodyAdvice(Lists.newArrayList(new RequestBodyAdvisor()));
        adapter.setResponseBodyAdvice(Lists.newArrayList(new ResponseBodyAdvisor()));
        return adapter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);

        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(jacksonObjectMapper());
            }
        }
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        return commonsMultipartResolver;
    }
}
