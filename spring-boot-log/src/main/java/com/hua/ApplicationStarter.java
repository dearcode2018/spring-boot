/**
  * @filename ApplicationStarter.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

 /**
 * @type ApplicationStarter
 * @description 应用启动器
 * @author qianye.zheng
 */
/* 导入资源 xml/properties配置文件 */
//@ImportResource({"classpath*:spring/application*.xml"})
@SpringBootApplication(scanBasePackages = {"com.hua"})
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
	}
	
	
	
	
	
	
}
