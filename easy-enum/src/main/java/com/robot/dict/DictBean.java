package com.robot.dict;


import java.io.Serializable;

/**
 * 字典枚举bean，存储枚举值的实际对象
 *
 * @author R
 */
public class DictBean implements Dict<Serializable> {
    private final Serializable code;
    private final String text;

    public DictBean(Serializable code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public Serializable getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }
}