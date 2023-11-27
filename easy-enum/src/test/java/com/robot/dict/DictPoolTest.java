package com.robot.dict;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class DictPoolTest {

    @Test
    void putDictException() {
        // 不允许非枚举类使用字典池
        try {
            DictPool.putDict(new Dict<Integer>() {
            }, 1, "1");
            fail("No exception thrown.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("不是枚举类"));
        }
    }

}