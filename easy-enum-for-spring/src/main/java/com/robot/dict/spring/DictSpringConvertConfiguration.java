package com.robot.dict.spring;


import com.robot.dict.spring.converter.DictToStringConverter;
import com.robot.dict.spring.converter.StringToDictConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * spring接口入参的Dict枚举转换配置
 *
 * @author R
 */
@Configuration
public class DictSpringConvertConfiguration implements WebMvcConfigurer {


    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 字典转换器
        registry.addConverter(new DictToStringConverter());
        registry.addConverterFactory(new StringToDictConverterFactory());
    }


}
