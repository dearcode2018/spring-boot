/**
  * @filename ApplicationStarter.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

 /**
 * @type ApplicationStarter
 * @description 应用启动器
 * @author qianye.zheng
 */
@SpringBootApplication(scanBasePackages = {"com.hua"})
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
