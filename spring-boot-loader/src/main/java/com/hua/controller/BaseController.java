/**
 * 描述: 
 * BaseController.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述: 
 * 
 * @author qye.zheng
 * BaseController
 */
//@Controller
public abstract class BaseController {
	
	/* apache commons log */
	protected Log log = LogFactory.getLog(this.getClass().getName());
	
	/** 请求参数 - 当前工作线程 */
	private static final ThreadLocal<List<Object>> REQUEST_PARAM = new ThreadLocal<>();

	/**
     * 
     * @description 
     * @param params
     * @author qianye.zheng
     */
	public static final void setParams(List<Object> params) {
    	REQUEST_PARAM.set(params);
    }

    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
	public static final List<Object> getParams() {
        return REQUEST_PARAM.get();
    }

    /**
     * 
     * @description 
     * @author qianye.zheng
     */
	public static final void clearParams() {
    	REQUEST_PARAM.remove();
    }
	
}
