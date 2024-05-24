package com.robot.dict.spring.mybatis;


import com.robot.dict.Dict;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 字典枚举类型处理器
 * 可支持实体类使用枚举属性，以及MybatisPlus查询条件中使用枚举值
 *
 * @author R
 */
public class DictTypeHandler<K, T extends Dict<K>> extends BaseTypeHandler<Dict<K>> {
    private final Class<T> enumType;
    private final Class<K> genericType;

    @SuppressWarnings("unchecked")
    public DictTypeHandler(Class<T> enumType) {
        if (enumType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enumType = enumType;
        // 获取枚举实现的Dict接口类型
        ParameterizedType genericType = Arrays.stream(enumType.getGenericInterfaces())
                .map(v -> (ParameterizedType) v)
                .filter(v -> v.getRawType() == Dict.class)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("orm中使用的枚举类必须实现Dict接口"));
        // 拿到Dict类型的泛型
        this.genericType = (Class<K>) genericType.getActualTypeArguments()[0];

    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Dict<K> parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getCode());
    }

    @Override
    public Dict<K> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        K code = rs.getObject(columnName, genericType);
        return Dict.getByCode(enumType, code);
    }

    @Override
    public Dict<K> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        K code = rs.getObject(columnIndex, genericType);
        return Dict.getByCode(enumType, code);
    }

    @Override
    public Dict<K> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        K code = cs.getObject(columnIndex, genericType);
        return Dict.getByCode(enumType, code);
    }
}
