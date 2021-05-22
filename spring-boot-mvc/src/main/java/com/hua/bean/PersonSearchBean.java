/**
 * 描述: 
 * PersonSearchBean.java
 * @author qye.zheng
 * 
 *  version 1.0
 */
package com.hua.bean;

import com.hua.constant.Month;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述: 
 * @author qye.zheng
 * 
 * PersonSearchBean
 */
@SuppressWarnings({"serial"})
@Data
@EqualsAndHashCode(callSuper=false)
public final class PersonSearchBean extends BaseBean
{
	private String name;
	
	private String password;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING)
	//private Duration duration;
	
	private Month value;
	
}
