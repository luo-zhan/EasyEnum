package com.robot.dict;

/**
 * 字典枚举bean，存储枚举值的实际对象
 *
 * @author R
 */
public class DictBean implements Dict<Object> {

    private final Object code;

    private final String text;

    public DictBean(Object code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public Object getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }
}