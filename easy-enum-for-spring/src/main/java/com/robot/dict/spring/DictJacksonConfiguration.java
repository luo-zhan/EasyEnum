package com.robot.dict.spring;

import com.robot.dict.spring.jackson.DictModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dict枚举的jackson序列化配置
 *
 * @author R
 */
@Configuration
public class DictJacksonConfiguration {

    @Bean("dictJsonMapperCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.modules(new DictModule());
    }

}
