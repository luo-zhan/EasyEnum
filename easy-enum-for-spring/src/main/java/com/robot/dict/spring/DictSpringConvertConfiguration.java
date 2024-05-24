package com.robot.dict.spring;


import com.robot.dict.spring.converter.DictToStringConverter;
import com.robot.dict.spring.converter.StringToDictConverterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * spring接口入参的Dict枚举转换配置
 *
 * @author R
 */
@ConditionalOnClass(WebMvcConfigurer.class)
@Configuration
public class DictSpringConvertConfiguration implements WebMvcConfigurer {

    /**
     * controller接收到枚举参数，将string类型的枚举值转换成枚举
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToDictConverterFactory());
    }

    /**
     * feign调用生成url时，会将入参枚举转换成string类型的枚举值
     */
    @Bean
    public FeignFormatterRegistrar dictFeignConverterRegistrar() {
        return registry -> registry.addConverter(new DictToStringConverter());
    }


}
