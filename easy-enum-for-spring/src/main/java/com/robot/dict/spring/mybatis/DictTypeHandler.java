package com.robot.dict.spring.mybatis;


import com.robot.dict.Dict;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * 字典枚举类型处理器
 * 可支持实体类使用枚举属性，以及MybatisPlus查询条件中使用枚举值
 *
 * @author R
 */
public class DictTypeHandler<K, T extends Dict<K>> extends BaseTypeHandler<Dict<K>> {
    private final Class<T> type;
    private final Class<K> genericType;

    @SuppressWarnings("unchecked")
    public DictTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.genericType = Stream.of(type.getGenericInterfaces())
                .map(ParameterizedTypeImpl.class::cast)
                .filter(genericInterface -> genericInterface.getRawType() == Dict.class)
                .findFirst()
                .map(genericInterface -> (Class<K>) genericInterface.getActualTypeArguments()[0])
                .orElseThrow(() -> new RuntimeException("orm中使用的枚举类必须实现Dict接口"));

    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Dict<K> parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getCode());
    }

    @Override
    public Dict<K> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        K code = rs.getObject(columnName, genericType);
        return Dict.getByCode(type, code);
    }

    @Override
    public Dict<K> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        K code = rs.getObject(columnIndex, genericType);
        return Dict.getByCode(type, code);
    }

    @Override
    public Dict<K> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        K code = cs.getObject(columnIndex, genericType);
        return Dict.getByCode(type, code);
    }
}
