package com.hua.constant;

 /**
 * @type IEnum
 * @author qianye.zheng
 */
public interface IEnum {

	String getValue();
	
	IEnum fromValue(final String value);
}
