/**
  * @filename QueryService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hua.annotation.DataSource;
import com.hua.datasource.DataSourceNames;
import com.hua.entity.CollegeStudent;
import com.hua.mapper.auto.CollegeStudentMapper;

/**
 * @type QueryService
 * @description 查询服务，无需事务
 * @author qianye.zheng
 */
@Service
public class QueryService
{
	
	@Resource
	private CollegeStudentMapper collegeStudentMapper;
	
	/**
	 * 
	 * @description 根据id查询
	 * 数据源: 不标注数据源，使用默认的数据源
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	public CollegeStudent get(final Integer id)
	{
		return collegeStudentMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 
	 * @description 根据id查询
	 * 数据源: first据源
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	public CollegeStudent getFromFirst(final Integer id)
	{
		return collegeStudentMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 
	 * @description 根据id查询
	 * 数据源: second据源
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.SECOND)
	public CollegeStudent getFromSecond(final Integer id)
	{
		return collegeStudentMapper.selectByPrimaryKey(id);
	}
	
}
