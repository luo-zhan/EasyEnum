package com.robot.dict.spring.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.robot.dict.Dict;

/**
 * 反序列化生成器
 * 因为Dict有众多枚举子类，所以需要该类去生成各个枚举的反序列化器
 *
 * @author R
 */
public class DictDeserializers extends SimpleDeserializers {
    @Override
    @SuppressWarnings("all")
    public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        JsonDeserializer<?> enumDeserializer = super.findEnumDeserializer(type, config, beanDesc);
        if (enumDeserializer != null) {
            return enumDeserializer;
        }
        if (Dict.class.isAssignableFrom(type)) {
            return new DictDeserializer((Class<? extends Dict>) type);
        }
        return null;
    }
}
