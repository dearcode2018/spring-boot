package com.hua.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 
 * @type DynamicDataSource
 * @description 动态数据源
 * @author qianye.zheng
 */
public class DynamicDataSource extends AbstractRoutingDataSource 
{
	
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 
     * @description 构造方法
     * @param defaultTargetDataSource
     * @param targetDataSources
     * @author qianye.zheng
     */
    public DynamicDataSource(final DataSource defaultTargetDataSource, 
    		final Map<Object, Object> targetDataSources) 
    {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 
     * @description 获取数据源
     * @return
     * @author qianye.zheng
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    /**
     * 
     * @description 
     * @param dataSource
     * @author qianye.zheng
     */
    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 
     * @description 
     * @author qianye.zheng
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }

}
