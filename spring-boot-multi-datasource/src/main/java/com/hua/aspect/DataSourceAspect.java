package com.hua.aspect;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.hua.annotation.DataSource;
import com.hua.datasource.DataSourceNames;
import com.hua.datasource.DynamicDataSource;

/**
 * @Aspect
 * @type DataSourceAspect
 * @description 数据源，切面处理类
 * @author qianye.zheng
 */
@Component
public class DataSourceAspect implements Ordered {
	
	protected final Logger logger = LogManager.getLogger(this.getClass().getName());

    /**
     * 
     * @description 
     * @author qianye.zheng
     */
    @Pointcut("@annotation(com.hua.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    /**
     * 
     * @description 
     * @param point
     * @return
     * @throws Throwable
     * @author qianye.zheng
     */
    @Around("dataSourcePointCut()")
    public Object around(final ProceedingJoinPoint point) throws Throwable {
    	final MethodSignature signature = (MethodSignature) point.getSignature();
    	final  Method method = signature.getMethod();

        final DataSource dataSource = method.getAnnotation(DataSource.class);
        if(null == dataSource)
        {
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        } else {
            DynamicDataSource.setDataSource(dataSource.name());
            logger.debug("set datasource is " + dataSource.name());
        }

        try {
        	
            return point.proceed();
        } finally {
        	// 清空数据源缓存
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
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
