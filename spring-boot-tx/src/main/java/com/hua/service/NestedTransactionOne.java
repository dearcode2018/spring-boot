/**
  * @filename NestedTransactionOne.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hua.entity.CollegeStudent;
import com.hua.mapper.auto.CollegeStudentMapper;

/**
 * @type NestedTransactionOne
 * @description 
 * @author qianye.zheng
 */
@Transactional
@Service
public class NestedTransactionOne
{
	
	@Resource
	private CollegeStudentMapper collegeStudentMapper;
	
	@Resource
	private NestedTransactionTwo nestedTransactionTwo;
	
	
	/**
	 * 异常:
	 * Transaction rolled back because it has been marked as rollback-only
	 * 原因: 内嵌事务
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	public void nested1()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来First");
		entity.setCredit(BigDecimal.valueOf(15.5));
		entity.setBirthday(new Date());
		entity.setAddress("广西省桂林市阳朔县大桂路345号");
		entity.setRemark(null);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		collegeStudentMapper.insertSelective(entity);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		try
		{
			nestedTransactionTwo.throwException();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	@Transactional
	public void nested2()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来First");
		entity.setCredit(BigDecimal.valueOf(15.5));
		entity.setBirthday(new Date());
		entity.setAddress("广西省桂林市阳朔县大桂路345号");
		entity.setRemark(null);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		collegeStudentMapper.insertSelective(entity);
		try
		{
			nestedTransactionTwo.throwException();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	public void nested3()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来First");
		entity.setCredit(BigDecimal.valueOf(15.5));
		entity.setBirthday(new Date());
		entity.setAddress("广西省桂林市阳朔县大桂路345号");
		entity.setRemark(null);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		collegeStudentMapper.insertSelective(entity);
		try
		{
			nestedTransactionTwo.throwExceptionWithTx();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	@Transactional
	public void nested4()
	{
		// 当前方法
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来First");
		entity.setCredit(BigDecimal.valueOf(15.5));
		entity.setBirthday(new Date());
		entity.setAddress("广西省桂林市阳朔县大桂路345号");
		entity.setRemark(null);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		collegeStudentMapper.insertSelective(entity);
		
		try
		{
			nestedTransactionTwo.throwExceptionWithTx();
		} catch (Exception e)
		{
			//e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	public void nested5()
	{
		nestedInner();
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	private void nestedInner()
	{
		// 当前方法
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来First");
		entity.setCredit(BigDecimal.valueOf(15.5));
		entity.setBirthday(new Date());
		entity.setAddress("广西省桂林市阳朔县大桂路345号");
		entity.setRemark(null);
		/*
		 * 保存一个实体，null的属性不会保存，会使用数据库默认值
		 *  通常情况下，insertSelectvie使用得较多
		 */
		collegeStudentMapper.insertSelective(entity);
		
		try
		{
			nestedTransactionTwo.throwExceptionWithTx();
		} catch (Exception e)
		{
			//e.printStackTrace();
		}
	}
	
	
}
