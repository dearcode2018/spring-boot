/**
  * @filename CustomizationBean.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

/**
 * @type CustomizationBean
 * @description 定制化
 * @author qianye.zheng
 */
// @Component
public class CustomizationBean
		implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
{

	/**
	 * @description 
	 * @param factory
	 * @author qianye.zheng
	 */
	@Override
	public void customize(ConfigurableServletWebServerFactory factory)
	{ 
		// 
		factory.setPort(8080);
	}

}
