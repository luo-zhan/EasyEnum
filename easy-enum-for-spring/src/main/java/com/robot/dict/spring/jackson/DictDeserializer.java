package com.robot.dict.spring.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.robot.dict.Dict;

import java.io.IOException;

@SuppressWarnings("all")
public class DictDeserializer extends JsonDeserializer<Dict> implements ContextualDeserializer {
    private Class<? extends Dict> enumClass;

    public DictDeserializer(Class<? extends Dict> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        // 根据上下文创建不同Dict实现类的反序列化器
        return this;
    }

    @Override
    public Dict<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return Dict.getByCode((Class) enumClass, p.getValueAsString());
    }
}