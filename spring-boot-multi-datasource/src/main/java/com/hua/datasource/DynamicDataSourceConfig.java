package com.hua.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @type DynamicDataSourceConfig
 * @description  动态数据源配置
 * @author qianye.zheng
 */
@Configuration
@MapperScan(basePackages = "com.hua.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
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
    	System.out.println("DynamicDataSourceConfig.firstDataSource()");
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
    	System.out.println("DynamicDataSourceConfig.secondDataSource()");
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
    @Bean("dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(final DataSource firstDataSource, 
    		final DataSource secondDataSource) {
    	final Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }
    
    /**
     * 
     * @description Sql会话工厂
     * @param dataSource
     * @return
     * @throws Exception
     * @author qianye.zheng
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(final @Qualifier("dynamicDataSource") 
    DataSource dataSource) throws Exception {
    	VFS.addImplClass(SpringBootVFS.class);
    	final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.hua" + ".entity");
        bean.setConfiguration(configuration());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*Mapper.xml"));
        // 分页拦截器
        final PageInterceptor pageInterceptor = new PageInterceptor();
        final Properties props = new Properties();
        props.put("helperDialect", "mysql");
        props.put("pageSizeZero", "true");
        props.put("reasonable", "true");
        props.put("supportMethodsArguments", "true");
        pageInterceptor.setProperties(props);
        bean.setPlugins(new Interceptor[] {pageInterceptor});
        
        return bean.getObject();
    }

    /**
     * 
     * @description 会话配置
     * @return
     * @author qianye.zheng
     */
    public org.apache.ibatis.session.Configuration configuration()
    {
    	final tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
    	final Properties properties = new Properties();
    	properties.put("not-empty", "false");
    	properties.put("safe-update", "true");
    	properties.put("safe-delete", "true");
    	properties.put("check-example-entity-class", "true");
    	configuration.setMapperProperties(properties);
    	
    	return configuration;
    }
    
    /**
     * 
     * @description Sql会话模板
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     * @author qianye.zheng
     */
    @Bean("sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(final @Qualifier("sqlSessionFactory") 
    	SqlSessionFactory sqlSessionFactory) throws Exception {
    	
        return new SqlSessionTemplate(sqlSessionFactory);
    }    
    
    /**
	 * 
	 * @description 事务管理器
	 * @param dataSource
	 * @return
	 * @author qianye.zheng
	 */
    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(final @Qualifier("dynamicDataSource") DataSource dataSource) 
    {
    	final DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource);
    	manager.setDefaultTimeout(600);
    	
        return manager;
    }
        
    
}
