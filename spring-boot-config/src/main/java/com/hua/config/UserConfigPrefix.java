/**
  * @filename UserConfigPrefix.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

 /**
 * @type UserConfigPrefix
 * @description 
 * @author qianye.zheng
 */
// 便于其他使用到的地方直接注入该对象
@Data
/*
 * 在启动器声明该注解，引入配置类，此配置类即可省略Bean的注册，即无需标注@Component
 * @EnableConfigurationProperties({UserConfigPrefix.class})
 */
//@Component
/* 使用 核心配置文件中的配置 application.properties， 
 * 高版本中已经去掉指定 自定义属性文件的 location字段 */
/*
 * @ConfigurationProperties 用法
 * 通过 @PropertySource 引入属性文件， @ConfigurationProperties 定义前缀即可
 * 
 * 
 *
 */
@ConfigurationProperties(prefix = "user", ignoreInvalidFields = true, ignoreUnknownFields = true)
@Validated
public class UserConfigPrefix
{
	
	/*
	 * like: 
	 * user.name
	 */
	private String name1;
	
	/* 格式 user.passwords[0] user.passwords[1]  */
	private List<String> passwords;
	
	private Integer age;
	
	private String address;
	
	private String remark;
	
	/*
	 * 加上 @Validated 注解之后，为空则抛异常
	 */
	//@NotNull
	private String noExist;
	
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
