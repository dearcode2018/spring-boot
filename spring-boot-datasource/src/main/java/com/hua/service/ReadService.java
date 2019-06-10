/**
  * @filename ReadService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hua.annotation.DataSource;
import com.hua.datasource.DataSourceNames;
import com.hua.entity.CollegeStudent;
import com.hua.mapper.auto.CollegeStudentMapper;

/**
 * @type ReadService
 * @description 读服务，无需事务
 * @author qianye.zheng
 */
// 整个对象的方法若没加事务注解，则继承该声明
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
@Service
public class ReadService
{
	
	@Resource
	private CollegeStudentMapper collegeStudentMapper;
	
	@Resource
	private WriteService writeService;
	
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
	
	// 主读少写的Service
	/*
	 * 
	 */
	/**
	 * @description 读多写少的服务
	 * 当前方法读，调用另外一个对象的写方法，其方法事务传播属性
	 * 声明为require_new
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	public CollegeStudent readManyWriteLittle(final Integer id)
	{
		
		// 读操作，假设很多
		collegeStudentMapper.selectByPrimaryKey(id);
		
		/*
		 * 少了写操作，调用其他对象的写方法，本方法的事务还是只读并且使用
		 * 自己声明的数据源
		 */
		writeService.insertWithSelftTx();
		
		return collegeStudentMapper.selectByPrimaryKey(id);
	}

}
