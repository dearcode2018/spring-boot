package com.hua.plugin;

import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 
 * @type CodeComment
 * @description 代码注释
 * @author qianye.zheng
 */
public interface CodeComment {

	/**
	 * 
	 * @description 字段注释
	 * @param element
	 * @param remark 注释内容
	 * @author qianye.zheng
	 */
    public default void fieldComment(final JavaElement element, final String remark) {
        if (StringUtility.stringHasValue(remark)) 
        {
            element.addJavaDocLine("/* ");
            final String[] remarkLines = remark.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                element.addJavaDocLine(" * " + remarkLine);
            }
            element.addJavaDocLine(" */");
        }
    }
    
	/**
	 * 
	 * @description 方法
	 * @param element
	 * @param remark 注释内容
	 * @author qianye.zheng
	 */
    public default void methodComment(final JavaElement element, final String remark) {
        if (StringUtility.stringHasValue(remark)) 
        {
            element.addJavaDocLine("/* ");
            final String[] remarkLines = remark.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                element.addJavaDocLine(" * " + remarkLine);
            }
            element.addJavaDocLine(" */");
        }
    }
    
}
