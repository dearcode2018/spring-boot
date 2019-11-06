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
	
}
