/**
  * @filename NestedTransactionTwo.java
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

import com.hua.entity.CollegeStudent;
import com.hua.mapper.auto.CollegeStudentMapper;

/**
 * @type NestedTransactionTwo
 * @description 
 * @author qianye.zheng
 */
@Service
public class NestedTransactionTwo
{
	
	@Resource
	private CollegeStudentMapper collegeStudentMapper;
	
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	public void throwException()
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
		
		// 有意制造异常
		String str = null;
		str.length();
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void throwExceptionWithTx()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setId(16);
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
	}
	
}
