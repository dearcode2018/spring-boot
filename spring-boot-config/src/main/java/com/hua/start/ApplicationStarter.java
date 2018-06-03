/**
  * @filename ApplicationStarter.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hua.config.UserConfig;

 /**
 * @type ApplicationStarter
 * @description 应用启动器
 * @author qianye.zheng
 */
@SpringBootApplication(scanBasePackages = {"com.hua"})
/*
 * 引入spring其他配置文件
 */
/* 引入自定义配置对象 */
//@EnableConfigurationProperties(value = {UserConfig.class})
//@ImportResource(locations = {"classpath:conf/xml/spring-config.xml"})
public class ApplicationStarter
{
	
	
	/**
	 * 
	 * @description  启动入口: 主方法
	 * @param args
	 * @author qianye.zheng
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(ApplicationStarter.class, args);
	}
	
	
}
