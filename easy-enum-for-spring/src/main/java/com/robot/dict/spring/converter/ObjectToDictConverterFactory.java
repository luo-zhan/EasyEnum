package com.robot.dict.spring.converter;


import com.robot.dict.Dict;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.io.Serializable;

/**
 * 字典枚举转换工厂
 *
 * @author R
 */
public abstract class ObjectToDictConverterFactory<K extends Serializable> implements ConverterFactory<K, Dict<K>> {


    @Override
    public <T extends Dict<K>> Converter<K, T> getConverter(Class<T> targetType) {
        return new DictConverter<>(targetType);
    }

    private static class DictConverter<T extends Dict<K>, K extends Serializable> implements Converter<K, T> {

        private final Class<T> dictType;

        public DictConverter(Class<T> dictType) {
            this.dictType = dictType;
        }

        @Override
        public T convert(K source) {
            return Dict.getByCode(dictType, source);
        }
    }
}