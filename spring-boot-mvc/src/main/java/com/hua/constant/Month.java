/**
 * 描述: 
 * Month.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述:  月份 - 枚举
 * 
 * @author qye.zheng
 * Month
 */
@Getter
@AllArgsConstructor
public enum Month {

	/*
	 * 枚举公开实例
	 */
	
	// 一月
	JANUARY("一月", "January"), 
	
	// 二月
	FEBRUARY("二月", "February"),
	
	// 三月
	MARCH("三月", "March"),
	
	// 四月
	APRIL("四月", "April"), 
	
	// 五月
	MAY("五月", "May"),
	
	// 六月
	JUNE("六月", "June"),
	
	// 七月
	JULY("七月", "July"), 
	
	// 八月
	AUGUST("八月", "August"),
	
	// 九月
	SEPTEMBER("九月", "September"),
	
	// 十月
	OCTOBER("十月", "October"), 
	
	// 十一月
	NOVEMBER("十一月", "November"),
	
	// 十二月
	DECEMBER("十二月", "December");
	
	/*
	 父类Enum也有一个name属性，
	 父类name的值存储的是每个枚举
	 实例的字面值
	 */
	// 枚举实例的名称 (中文)
	private String name;
	
	// 值，code (英文/英文缩写)
	private String value;

	private Month() {}
	
	@JsonCreator
	public static Month fromValue(String value) {
		for (Month e : values()) {
			if (e.getValue().equals(value)) return e;
		}
		
		return null;
	}
	
}
