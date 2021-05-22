package com.hua.configure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @type ConditionOnConfiguration
 * @author qianye.zheng
 */
@Configuration
public class ConditionOnConfiguration {

	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Bean
	@ConditionalOnProperty(prefix = "a.b", value = {"name"})
	public Object value() {
		System.out.println("ConditionOnConfiguration.value()");
		return new Object();
	}
	
	
}
