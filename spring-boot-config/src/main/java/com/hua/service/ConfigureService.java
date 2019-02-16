/**
  * @filename ConfigureService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.hua.config.ThirdConfig;

/**
 * @type ConfigureService
 * @description 
 * @author qianye.zheng
 */
@Service
public class ConfigureService
{
	
	@Value("${some.singleA}")
	private String singleA;
	
	@Value("${some.singleB}")
	private String singleB;
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	public void singleConfigure()
	{
		System.out.println("single1 = " + singleA);
		System.out.println("single2 = " + singleB);
		
	}

	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@ConfigurationProperties(prefix = "user", ignoreInvalidFields = true, ignoreUnknownFields = true)
	@Bean
	public ThirdConfig thirdConfig()
	{
		return new ThirdConfig();
	}
	
	/**
	* @return the singleA
	*/
	public String getSingleA()
	{
		return singleA;
	}

	/**
	* @param singleA the singleA to set
	*/
	public void setSingleA(String singleA)
	{
		this.singleA = singleA;
	}

	/**
	* @return the singleB
	*/
	public String getSingleB()
	{
		return singleB;
	}

	/**
	* @param singleB the singleB to set
	*/
	public void setSingleB(String singleB)
	{
		this.singleB = singleB;
	}
	
	/**
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	public String toString()
	{
		return new ReflectionToStringBuilder(this).toString();
	}
	
}
