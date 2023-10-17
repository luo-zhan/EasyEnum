package com.robot.dict.spring;

import com.robot.dict.spring.mybatis.DictTypeHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Dict枚举对Mybatis的支持
 *
 * @author R
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
public class DictMybatisConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        typeHandlerRegistry.setDefaultEnumTypeHandler(DictTypeHandler.class);
    }

}
