package com.robot.dict;


/**
 * 字典枚举bean，存储枚举值的实际对象
 *
 * @author R
 */
public class DictBean {
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
     * <p>在枚举选项上使用@Deprecated注解标志过期
     * <p>该属性不会被序列化
     */
    private final transient boolean isDeprecated;

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


    public Object getCode() {
        return code;
    }


    public String getText() {
        return text;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

}