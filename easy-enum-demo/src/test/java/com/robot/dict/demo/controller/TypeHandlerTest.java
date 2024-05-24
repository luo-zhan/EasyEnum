package com.robot.dict.demo.controller;

import com.robot.dict.demo.enums.Sex;
import com.robot.dict.spring.mybatis.DictTypeHandler;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author robot.luo
 */
public class TypeHandlerTest {
    @Test
    public void test_typeHandler() throws Exception {
        // Sex实现了Dict<Integer>，DictTypeHandler中需要正确获取Sex和Integer类型
        DictTypeHandler<Integer, Sex> dictTypeHandler = new DictTypeHandler<>(Sex.class);
        Field enumType = DictTypeHandler.class.getDeclaredField("enumType");
        Field genericType = DictTypeHandler.class.getDeclaredField("genericType");
        enumType.setAccessible(true);
        genericType.setAccessible(true);
        Object enumTypeValue = enumType.get(dictTypeHandler);
        Object genericTypeValue = genericType.get(dictTypeHandler);
        assertEquals(enumTypeValue, Sex.class);
        assertEquals(genericTypeValue, Integer.class);

    }
}
