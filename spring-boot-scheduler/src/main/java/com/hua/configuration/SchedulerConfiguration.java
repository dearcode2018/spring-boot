/**
  * @filename SchedulerConfiguration.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @type SchedulerConfiguration
 * @description 
 * @author qianye.zheng
 */
@Configuration
public class SchedulerConfiguration
{
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	@QuartzDataSource
	@Bean
	public DataSource dataSource()
	{
		 return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
}
