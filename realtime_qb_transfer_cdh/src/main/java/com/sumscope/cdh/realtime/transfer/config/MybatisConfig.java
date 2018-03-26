package com.sumscope.cdh.realtime.transfer.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by liu.yang on 2017/9/27.
 */
@Configuration
@MapperScan(basePackages = "com.sumscope.cdh.realtime.transfer")
public class MybatisConfig {
    @Autowired
    private Environment configParam;

    /**
     * Inject connection pool
     * @return
     * @throws Exception
     */
    @Bean
    public DataSource getDataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName", configParam.getProperty("jdbc.driverClassName"));
        props.put("url", configParam.getProperty("jdbc.url"));
        props.put("username", configParam.getProperty("jdbc.username"));
        props.put("password", configParam.getProperty("jdbc.password"));
        props.put("initialSize", configParam.getProperty("connector.pool.init.size"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * Create session factory
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource);
        fb.setTypeAliasesPackage(configParam.getProperty("mybatis.type-aliases-package"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(configParam.getProperty("mybatis.mapper-locations")));
        return fb.getObject();
    }
}