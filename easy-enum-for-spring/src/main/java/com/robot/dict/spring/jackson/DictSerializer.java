package com.robot.dict.spring.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.robot.dict.Dict;

import java.io.IOException;

/**
 * Dict序列化器
 *
 * @author Robot
 */
@SuppressWarnings("all")
public class DictSerializer extends JsonSerializer<Dict> {

    @Override
    public Class<Dict> handledType() {

        return Dict.class;
    }

    @Override
    public void serialize(Dict dict, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(dict.getCode());

    }

    @Override
    public void serializeWithType(Dict value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.VALUE_STRING));
        serialize(value, gen, serializers);
        typeSer.writeTypeSuffix(gen, typeIdDef);
    }
}