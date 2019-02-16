/**
  * @filename ApplicationStarter.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.hua.config.UserConfigPrefix;

 /**
 * @type ApplicationStarter
 * @description 应用启动器
 * @author qianye.zheng
 */
/* 显式导入资源: XML文件 */
@ImportResource({"classpath:conf/xml/spring-config.xml"})
/*
 * @EnableConfigurationProperties 用法
 * 
 * @EnableConfigurationProperties 引入@ConfigurationProperties 注册的配置类，配置类可以免去声明Bean的注解(@Component ...)
 * 
 */
@EnableConfigurationProperties({UserConfigPrefix.class})
/*
 * @PropertySource 可以在任意一个地方声明，声明一次就可以在任意一个地方使用，
 * 注意，在其他地方 例如XML文件不要再声明 PropertyPlaceholderConfigurer 对象，否则 @PropertySource 导入的属性文件
 * 将无法生效
 * 注意 @PropertySource 无法引入YAML文件，配置只能定义为属性文件
 */
@PropertySource(value = {"classpath:conf/properties/single.properties", "classpath:conf/properties/project.properties", }, ignoreResourceNotFound = false, encoding = "UTF-8")
//@PropertySource(value = {"classpath:conf/properties/project.properties"})
/* @SpringBootApplication已经包含 @Configuration @EnableAutoConfiguration @ComponentScan */
//@SpringBootApplication(scanBasePackages = {"com.hua"})
/* 该类在根包(basePackage)下，则无须再指定scanBasePackages */
@SpringBootApplication
/* 启动指定特性 */
//@EnableDiscoveryClient
//@EnableCircuitBreaker
//@EnableHystrixDashboard //
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
		
		//SpringApplication springApplication = new SpringApplication(ApplicationStarter.class);
		//springApplication.run(args);
		// 无条件添加活动配置文件
		//springApplication.setAdditionalProfiles("test", "prod");
		// 禁用命令行属性
		//springApplication.setAddCommandLineProperties(false);
	}
	
	
	
	
	
	
}
