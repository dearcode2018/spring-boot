/**
  * @filename LocalDataSourceConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.wehotel.uni.user.configuration;

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
import com.wehotel.uni.user.common.Constant;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @type LocalDataSourceConfig
 * @description 本地主数据源配置
 * @author qianye.zheng
 */
@Configuration
@MapperScan(basePackages = "com.wehotel.uni.user.mapper.local", sqlSessionTemplateRef = "localSqlSessionTemplate")
public class LocalDataSourceConfig
{
	
	/**
	 * 
	 * @description 数据源
	 * @return
	 * @author qianye.zheng
	 */
	@Bean(name = "localDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.hikari.local")
	@Primary
	public DataSource setDataSource()
	{
		 return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	/**
	 * 
	 * @description 事务管管理器
	 * @param dataSource
	 * @return
	 * @author qianye.zheng
	 */
    @Bean(name = "localTransactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(final @Qualifier("localDataSource") DataSource dataSource) {
    	final DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource);
    	manager.setDefaultTimeout(600);
    	
        return manager;
    }
    
    /**
     * 
     * @description Sql会话工厂
     * @param dataSource
     * @return
     * @throws Exception
     * @author qianye.zheng
     */
    @Bean(name = "localSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(final @Qualifier("localDataSource") DataSource dataSource) throws Exception {
    	VFS.addImplClass(SpringBootVFS.class);
    	final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(Constant.BASE_PACKAGE + ".entity");
        bean.setConfiguration(configuration());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/local/**/*Mapper.xml"));
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
    @Bean(name = "localSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(final @Qualifier("localSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    	
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    
}
