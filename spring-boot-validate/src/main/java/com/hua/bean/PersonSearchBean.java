/**
 * 描述: 
 * PersonSearchBean.java
 * @author qye.zheng
 * 
 *  version 1.0
 */
package com.hua.bean;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hua.validate.ObjectCheck;

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
@ObjectCheck(message = "对象参数不正确")
public final class PersonSearchBean extends BaseBean
{
	/**
	 * @NotNull: 接受任意类型，对象不能为null，若为String类型，可以为空字符串""
	 * 
	 * @NotBlank: 接受字符类型，不能为null，至少包含空字符串""
	 */
	@NotNull(message = "名称不能为空")
	private String name;
	
	//@NotBlank(message = "密码不能为空")
	private String password;
	
	//@Email
	//@Length
	private String email;
	
	private String email2;
	
	
}
