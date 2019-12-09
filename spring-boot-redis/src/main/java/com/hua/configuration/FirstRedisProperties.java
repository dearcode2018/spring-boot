/**
  * @filename FirstRedisProperties.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @type FirstRedisProperties
 * @description 
 * @author qianye.zheng
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class FirstRedisProperties
{

	private String host;
	
	private String password;
	
	private Integer database;
	
	/* 连接超时时间，单位: 毫秒 */
	private Long connectTimeout;
	
	/* 读超时时间，单位: 毫秒 */
	private Long readTimeout;
	
	/** 连接池配置(Jedis) */
	/* 最小空闲数 */
	private Integer minIdle;
	
	/* 最大空闲数 */
	private Integer maxIdle;
	
	/* 最大连接数，单位: 毫秒 */
	private Integer maxTotal;
	
	/* 创建时是否测试可用 */
	private Boolean testOnCreate;
	
	/* 最大等待时间，单位: 毫秒 */
	private Long maxWaitMillis;
}
