package com.hua.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @type DynamicDataSourceConfig
 * @description  多数据源配置
 * @author qianye.zheng
 */
@Configuration
public class DynamicDataSourceConfig 
{
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
    @Bean
    @ConfigurationProperties("datasource.first")
    public DataSource firstDataSource(){
    	
    	 return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    @Bean
    @ConfigurationProperties("datasource.second")
    public DataSource secondDataSource(){
    	
    	 return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * 
     * @description 
     * @param firstDataSource
     * @param secondDataSource
     * @return
     * @author qianye.zheng
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(final DataSource firstDataSource, final DataSource secondDataSource) {
    	final Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }
}
