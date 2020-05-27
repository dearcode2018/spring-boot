/**
  * @filename CustomConfigBean.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @type CustomConfigBean
 * @description 
 * @author qianye.zheng
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "custom", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Validated
public class CustomConfigBean {
	
	/** 基本类型 */
	private String id;
	
	private Long score1;
	
	private double score2;
	
	/** 资源定位 */
	private URI uri;
	
	private URL url;
	
	/** 日期、时间 */
	private Duration timeout;
	
	private Duration connectTimeout;
	
	private Duration readTimeout;
	
	/* Date LocalDate LocalDateTime 不支持解析 */
	private LocalDate date;
	
	private LocalDateTime dateTime;
	
	/** 自定义扩展 */
	private ExternalParam param;
	
	/* 扩展参数，数组 */
	private List<ExternalParam> params;
	
	
	/**
	 * 
	 * @type ExternalParam
	 * @description 
	 * @author qianye.zheng
	 */
	@Data
	public static final class ExternalParam {
		
		private String name;
		
		private Duration timeout;
		
	}
	
}
