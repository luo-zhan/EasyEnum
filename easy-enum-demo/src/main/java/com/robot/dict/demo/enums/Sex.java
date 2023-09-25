package com.robot.dict.demo.enums;

import com.robot.dict.Dict;

/**
 * 性别枚举示例
 */
public enum Sex implements Dict<Integer> {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(3, "未知");

    Sex(Integer code, String text) {
        // 一个init方法搞定
        init(code, text);
    }

}
