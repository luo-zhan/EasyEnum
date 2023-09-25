package com.robot.dict.spring.converter;


import com.robot.dict.Dict;
import org.springframework.core.convert.converter.Converter;

/**
 * 字典转字符串转换器
 *
 * @author R
 */
public class DictToStringConverter implements Converter<Dict<?>, String> {
    @Override
    public String convert(Dict<?> source) {
        return source.getCode().toString();
    }
}

