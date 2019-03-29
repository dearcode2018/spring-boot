/**
  * @filename WriteService.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hua.annotation.DataSource;
import com.hua.datasource.DataSourceNames;
import com.hua.entity.CollegeStudent;
import com.hua.mapper.auto.CollegeStudentMapper;

/**
 * @type WriteService
 * @description 写服务，需事务支持
 * @author qianye.zheng
 */
@Service
public class WriteService
{

	@Resource
	private CollegeStudentMapper collegeStudentMapper;
	
	@Resource
	private DataSourceTransactionManager transactionManager;
	
	/**
	 * 
	 * @description 
	 * 数据源: 不标注数据源，使用默认的数据源
	 * @author qianye.zheng
	 */
	@Transactional
	public void insert()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来2");
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
	 * 数据源: first据源
	 * @author qianye.zheng
	 */
	@Transactional
	@DataSource(name = DataSourceNames.FIRST)
	public void insertToFirst()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来First");
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
	@Transactional
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
	 * @description 手工事务
	 * 数据源: second据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.SECOND)
	public void insertWithManualTx()
	{
		final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 事物隔离级别，开启新事务
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		// 获得事务状态
		final TransactionStatus st = transactionManager.getTransaction(def);		
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
		
		// 提交事务
		transactionManager.commit(st);			
	}		
	
	
	
}
