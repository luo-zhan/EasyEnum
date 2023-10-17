package com.robot.dict;


/**
 * 字典枚举bean，存储枚举值的实际对象
 *
 * @author R
 */
public class DictBean implements Dict<Object> {
    /**
     * 字典code
     */
    private final Object code;
    /**
     * 字典text
     */
    private final String text;
    /**
     * 是否过期
     * 一般来说，过期的字典项不能用于新增，而查询时需要作文本翻译
     */
    private final boolean isDeprecated;

    public DictBean(Object code, String text) {
        this.code = code;
        this.text = text;
        this.isDeprecated = false;
    }

    public DictBean(Object code, String text, boolean isDeprecated) {
        this.code = code;
        this.text = text;
        this.isDeprecated = isDeprecated;
    }

    @Override
    public Object getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }

    public boolean getIsDeprecated() {
        return isDeprecated;
    }

}