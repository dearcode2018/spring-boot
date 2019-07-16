package com.hua.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * @type DataSource
 * @description 数据源
 * @author qianye.zheng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
   
	/**
	 * 
	 * @description 数据源名称
	 * @return
	 * @author qianye.zheng
	 */
	String name() default "";
}
