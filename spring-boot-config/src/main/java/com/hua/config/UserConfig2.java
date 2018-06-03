/**
  * @filename UserConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

 /**
 * @type UserConfig
 * @description 
 * @author qianye.zheng
 */
// 便于其他使用到的地方直接注入该对象
@Configuration
@PropertySource(value = {"classpath:conf/properties/project.properties"})
public class UserConfig2
{
	/*
	 * 需要在该实体中定义好key
	 */
	/*
	 * like: 
	 * user.name
	 */
	@Value("${user2.name}")
	private String name;
	
	@Value("${user2.password}")
	private String password;
	
	@Value("${user2.age}")
	private Integer age;
	
	@Value("${user2.address}")
	private String address;
	
	private String remark;

	/**
	 * @return the name
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public final String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the age
	 */
	public final Integer getAge()
	{
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public final void setAge(Integer age)
	{
		this.age = age;
	}

	/**
	 * @return the address
	 */
	public final String getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public final void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the remark
	 */
	public final String getRemark()
	{
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public final void setRemark(String remark)
	{
		this.remark = remark;
	}
	
}
