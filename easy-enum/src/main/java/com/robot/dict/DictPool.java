package com.robot.dict;

import java.io.Serializable;
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
     * 放入字典和对应的code、text
     */
    static <T extends Serializable> void putDict(Dict<T> dict, T code, String text) {
        DICT_MAP.put(dict, new DictBean(code, text));
    }

    /**
     * 根据code取出字典
     */
    @SuppressWarnings("unchecked")
    static <K extends Dict<T>, T extends Serializable> Dict<T> getDict(K dict) {
        return (Dict<T>) DICT_MAP.get(dict);
    }

}
