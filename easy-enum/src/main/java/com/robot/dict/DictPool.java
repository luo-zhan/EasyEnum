package com.robot.dict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典池，所有Dict的实现类都会在这里注册
 *
 * @author R
 */
class DictPool {
    private DictPool() {
    }

    /**
     * 存储所有字典
     */
    private static final Map<Dict<?>, DictBean> DICT_MAP = new ConcurrentHashMap<>();
    /**
     * 枚举类和对应的字典集合
     */
    private static final Map<Class<?>, List<DictBean>> DICT_CLASS_MAP = new ConcurrentHashMap<>();

    /**
     * 放入字典和对应的code、text
     * 此步骤线程安全
     */
    static <T> void putDict(Dict<T> dict, T code, String text) {
        DictBean dictBean = new DictBean(code, text);
        DICT_MAP.put(dict, dictBean);
        // 同一个枚举类初始化一定是单线程的，所以使用ArrayList即可
        DICT_CLASS_MAP.computeIfAbsent(dict.getClass(), k -> new ArrayList<>()).add(dictBean);
    }

    /**
     * 根据枚举实例取出字典Bean实例
     */
    static DictBean getDict(Dict<?> dict) {
        return DICT_MAP.get(dict);
    }

    /**
     * 根据class获取字典集合
     */
    static List<DictBean> getAll(Class<? extends Dict<?>> clazz) {
        // 触发实例化枚举对象，避免并发问题导致未实例化拿不到数据，这比任何加锁方式都要简单快速
        clazz.getEnumConstants();
        return DICT_CLASS_MAP.get(clazz);
    }

}
