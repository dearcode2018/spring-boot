/**
  * @filename TxServiceTwo.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hua.annotation.DataSource;
import com.hua.datasource.DataSourceNames;
import com.hua.entity.CollegeStudent;
import com.hua.mapper.auto.CollegeStudentMapper;
import com.hua.util.JacksonUtil;

/**
 * @type TxServiceTwo
 * @description 
 * @author qianye.zheng
 */
@Transactional
@Service
public class TxServiceTwo
{

	@Resource
	private CollegeStudentMapper collegeStudentMapper;
	
	@Resource
	private TxServiceOne txServiceOne;
	
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
	
	/**
	 * 
	 * @description 根据id查询
	 * 数据源: second据源
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@DataSource(name = DataSourceNames.SECOND)
	public CollegeStudent getFromSecond2(final Integer id)
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DataSource(name = DataSourceNames.SECOND)
	public CollegeStudent getFromSecond3(final Integer id)
	{
		return collegeStudentMapper.selectByPrimaryKey(id);
	}	
	
	/**
	 * 
	 * @description 
	 * 数据源: second据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.SECOND)
	public void insertToSecond()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来Second");
		entity.setCredit(new BigDecimal(15.5));
		entity.setBirthday(new Date());
		entity.setAddress("广西省桂林市阳朔县大桂路345号");
		//entity.setType((byte) 1);
		entity.setRemark(null);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		collegeStudentMapper.insertSelective(entity);
	}	
	
	/**
	 * 
	 * @description 
	 * 数据源: second据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.SECOND)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertToSecond2()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来Second");
		entity.setCredit(new BigDecimal(15.5));
		entity.setBirthday(new Date());
		entity.setAddress("广西省桂林市阳朔县大桂路345号");
		//entity.setType((byte) 1);
		entity.setRemark(null);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		collegeStudentMapper.insertSelective(entity);
	}	
	
	/**
	 * 
	 * @description 
	 * 数据源: second据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.SECOND)
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void insertToSecond3()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来Second");
		entity.setCredit(new BigDecimal(15.5));
		entity.setBirthday(new Date());
		entity.setAddress("广西省桂林市阳朔县大桂路345号");
		//entity.setType((byte) 1);
		entity.setRemark(null);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		collegeStudentMapper.insertSelective(entity);
	}
	
	/**
	 * 
	 * @description 不声明数据源，使用默认数据源
	 * 数据源: second据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void getFromDefaultDataSource()
	{
		CollegeStudent entity = collegeStudentMapper.selectByPrimaryKey(3);
		System.out.println(JacksonUtil.writeAsString(entity));
	}		
	
}
