package com.robot.dict.spring.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.robot.dict.Dict;

public class DictModule extends SimpleModule {
    public DictModule() {
        super.addSerializer(Dict.class, new DictSerializer());
        super.setDeserializers(new DictDeserializers());
    }

}
