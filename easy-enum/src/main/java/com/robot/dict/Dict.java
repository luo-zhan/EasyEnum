package com.robot.dict;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字典接口
 * <p>
 * 自定义的字典枚举类实现本接口后可省略属性code和text，以及对应的get方法
 * 在构造方法中只需调用init方法即可初始化
 *
 * @author R
 */
public interface Dict<T> {

    /**
     * 初始化
     *
     * @param code 字典编码
     * @param text 字典文本
     */
    default void init(T code, String text) {
        DictPool.putDict(this, code, text);
    }

    /**
     * 获取code
     *
     * @return 字典编码
     */
    @SuppressWarnings("unchecked")
    default T getCode() {
        return (T) DictPool.getDict(this).getCode();
    }

    /**
     * 获取文本
     *
     * @return 字典文本
     */
    default String getText() {
        return DictPool.getDict(this).getText();
    }


    /**
     * 通过code获取value
     *
     * @param clazz 枚举class
     * @param code  code
     * @return text，找不到返回null
     */
    static <T> String getTextByCode(Class<? extends Dict<T>> clazz, T code) {
        return Stream.of(clazz.getEnumConstants())
                .filter((Dict<T> e) -> e.getCode().equals(code))
                .map(Dict::getText)
                .findAny().orElse(null);
    }

    /**
     * 通过text获取code
     *
     * @param clazz 枚举class
     * @param text  text
     * @return code，找不到返回null
     */
    static <T> T getCodeByText(Class<? extends Dict<T>> clazz, String text) {
        return Stream.of(clazz.getEnumConstants())
                .filter(e -> e.getText().equals(text))
                .map(Dict::getCode)
                .findAny().orElse(null);
    }

    /**
     * 通过code获取字典枚举实例
     *
     * @param clazz 枚举class
     * @param code  code
     * @param <T>   字典code类型
     * @param <K>   枚举类型
     * @return 字典枚举实例，找不到返回null
     */
    static <T, K extends Dict<T>> K getByCode(Class<K> clazz, T code) {
        return Stream.of(clazz.getEnumConstants())
                .filter(e -> e.getCode().toString().equals(String.valueOf(code)))
                .findAny().orElse(null);
    }

    /**
     * 获取所有字典枚举项（常用下拉框数据请求）
     *
     * @param clazz 字典枚举类
     * @return List
     */
    static <T> List<DictBean> getAll(Class<? extends Dict<T>> clazz) {
        return getAll(clazz, false);
    }

    /**
     * 获取所有字典枚举项（常用下拉框数据请求）
     * 可以选择是否包含废弃枚举项
     *
     * @param clazz               字典枚举类
     * @param isExcludeDeprecated 是否排除废弃选项，枚举项上使用@Deprecated注解标识废弃
     * @return List
     */
    static <T> List<DictBean> getAll(Class<? extends Dict<T>> clazz, boolean isExcludeDeprecated) {
        return DictPool.getAll(clazz, isExcludeDeprecated);
    }

    /**
     * 获取给定的字典枚举项（常用下拉框数据请求）
     *
     * @param enums 可指定需要哪些项
     * @return List
     */
    @SafeVarargs
    static <E extends Dict<?>> List<DictBean> getItems(E... enums) {
        return Stream.of(enums)
                .map(DictPool::getDict)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有字典枚举项，除开指定的枚举
     *
     * @param excludeEnums 指定排除的枚举
     * @return List
     */
    @SuppressWarnings("unchecked")
    static <E extends Dict<?>> List<DictBean> getItemsExclude(E... excludeEnums) {
        Class<? extends Dict<?>> enmuClass = (Class<? extends Dict<?>>) excludeEnums.getClass().getComponentType();
        List<DictBean> allDict = DictPool.getAll(enmuClass, false);
        List<?> excluceCodeList = Stream.of(excludeEnums).map(Dict::getCode).collect(Collectors.toList());
        return allDict.stream()
                .filter(dictBean -> !excluceCodeList.contains(dictBean.getCode()))
                .collect(Collectors.toList());
    }


    /**
     * 将对象集合转换成字典集合
     * 常用于下拉框数据生成
     *
     * @param list          原集合
     * @param codeGenerator 字典编码生成函数
     * @param textGenerator 字典文本生成函数
     * @return 字典集合
     */
    static <T, K> List<DictBean> parseList(List<T> list, Function<T, K> codeGenerator, Function<T, String> textGenerator) {
        return list.stream().map(v -> new DictBean(codeGenerator.apply(v), textGenerator.apply(v))).collect(Collectors.toList());
    }

}
