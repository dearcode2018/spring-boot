/**
  * @filename SomeQuery.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hua.annotation.AddCheck;
import com.hua.annotation.QueryCheck;
import com.hua.annotation.SpecialCheck;
import com.hua.annotation.UpdateCheck;

import lombok.Data;

/**
 * @type SomeQuery
 * @description 
 * @author qianye.zheng
 */
@Data
public final class SomeQuery {
    
    /**
     * @NotNull: 接受任意类型，对象不能为null，若为String类型，可以为空字符串""
     * 
     * @NotBlank: 接受字符类型，不能为null，至少包含空字符串""
     */
    @NotNull(message = "名称不能为空", groups = AddCheck.class)
    private String name;
    
    @NotBlank(message = "密码不能为空", groups = UpdateCheck.class)
    private String password;
    
    @NotBlank(message = "关键字不能为空", groups = QueryCheck.class)
    private String keyword;

    @NotNull(message = "关键字不能为空", groups = {SpecialCheck.class})
    private Long specialCheck;   
    
    //@Email
    private String email;
    
}
