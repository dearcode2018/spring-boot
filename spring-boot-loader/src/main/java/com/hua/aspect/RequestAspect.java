package com.hua.aspect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.hua.controller.BaseController;

/**
 * @type RequestAspect
 * @description 请求切面处理
 * @author qianye.zheng
 */
@Aspect
@Configuration
public class RequestAspect implements Ordered {
	
	protected final Logger logger = LogManager.getLogger(this.getClass().getName());

	/* 忽略的类型名称-简单名称，星号(*)表示忽略所有的，相当于关闭这个功能 */
	@Value("${request.param.ignore.typeNames:HttpServletRequest,HttpServletResponse,MultipartFile,Errors}")
	private List<String> ignoreTypeNames;
	
	/**
	 * 
	 * @description 
	 * @param point 连接点
	 * @return
	 * @author qianye.zheng
	 */
    @Before("execution(* com.hua.controller..*.*(..))")
    public void before(final JoinPoint point) {
    	if (ignoreTypeNames.contains("*")) { // 返回空集合
    		BaseController.setParams(Collections.emptyList());
    	} else {
    		// 把参数存储到当前线程上下文
    		final List<Object> params = new ArrayList<>();
    		for (Object param : point.getArgs()) {
    			if (param.getClass().isPrimitive() || param instanceof CharSequence) { // 基本类型/字符串无需判断，直接加入
    				params.add(param);
    				continue;
    			}
    			// 忽略指定类型
    			final Class<?>[] interfaceClass = param.getClass().getInterfaces();
    			boolean add = true;
    			for (Class<?> clazz : interfaceClass) {
    				if (ignoreTypeNames.contains(clazz.getSimpleName())) {
    					add = false;
    					break;
    				}
    			}
    			if (add) {
    				params.add(param);
    			}
    		}
    		BaseController.setParams(params);
    	}
    }

    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    @Override
    public int getOrder() {
    	
        return 1;
    }
}
