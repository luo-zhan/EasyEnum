package com.robot.dict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 字典池，所有Dict的实现类都会在这里注册
 *
 * @author R
 */
interface DictPool {
    /**
     * 存储所有字典
     */
    Map<Dict<?>, DictBean> DICT_MAP = new ConcurrentHashMap<>();
    /**
     * 枚举类和对应的字典集合
     */
    Map<Class<?>, List<DictBean>> DICT_CLASS_ITEMS_MAP = new ConcurrentHashMap<>();

    /**
     * 放入字典和对应的code、text
     * 此步骤线程安全
     */
    static <T> void putDict(Dict<T> dict, T code, String text) {
        Class<?> dictClass = dict.getClass();
        boolean isEnumDeprecated = false;
        try {
            isEnumDeprecated = dictClass.getDeclaredField(((Enum<?>) dict).name()).isAnnotationPresent(Deprecated.class);
        } catch (ClassCastException | NoSuchFieldException e) {
            throw new RuntimeException(dictClass + "不是枚举类", e);
        }
        DictBean dictBean = new DictBean(code, text, isEnumDeprecated);
        DICT_MAP.put(dict, dictBean);
        // 同一个枚举类初始化一定是单线程的，所以使用ArrayList即可
        DICT_CLASS_ITEMS_MAP.computeIfAbsent(dictClass, k -> new ArrayList<>()).add(dictBean);
    }

    /**
     * 根据枚举实例取出字典Bean实例
     */
    static DictBean getDict(Dict<?> dict) {
        return DICT_MAP.get(dict);
    }


    /**
     * 根据class获取字典集合，可以选择是否排除过期选项
     * 枚举上使用@Deprecated注解标识过期选项
     */
    static List<DictBean> getAll(Class<? extends Dict<?>> clazz, boolean isExcludeDeprecated) {
        // 触发实例化枚举对象，避免并发问题导致未实例化拿不到数据，这比任何加锁方式都要简单快速
        clazz.getEnumConstants();
        List<DictBean> dictBeans = DICT_CLASS_ITEMS_MAP.get(clazz);
        if (isExcludeDeprecated) {
            return dictBeans.stream().filter(dictBean -> !dictBean.getIsDeprecated()).collect(Collectors.toList());
        }
        return dictBeans;
    }


}
