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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hua.annotation.DataSource;
import com.hua.datasource.DataSourceNames;
import com.hua.entity.CollegeStudent;
import com.hua.mapper.auto.CollegeStudentMapper;
import com.hua.util.JacksonUtil;

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
	private ReadService readService;
	
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
		entity.setName("赵东来manual tx");
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
	
	/**
	 * 
	 * @description 没有使用注解声明事务
	 * 由于hikari数据源无法关闭autoCommit，因此没有标注@Transactional
	 * 到最后数据也会持久化到数据库.
	 * 数据源: second据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.SECOND)
	public void insertWithoutTransactionalAnno()
	{
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来withoutTransactionalAnno");
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
	@Transactional(readOnly = true)
	@DataSource(name = DataSourceNames.SECOND)
	public void insertWithReadOnlyTxAnno()
	{
		/*
		 * 事务注解声明为只读，DML操作将抛异常
			### Error updating database.  Cause: java.sql.SQLException: Connection is read-only. 
			Queries leading to data modification are not allowed
		 */
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来 ready only tx anno");
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
	// timeout = 2 表示 2 秒后超时，触发rollback()
	@Transactional(timeout = 2)
	//@Transactional
	@DataSource(name = DataSourceNames.SECOND)
	public void insertWithTxTimeOut()
	{
		/*
		 * mysql 默认超时时间: 
		 * innodb_lock_wait_timeout
		 */
		/*
		 * 超时间会抛异常
		 * org.springframework.transaction.TransactionTimedOutException: 
		 * Transaction timed out: deadline was Sat Mar 30 16:05:06 CST 2019
		 */
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来 rollback after sepecial seconds");
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
	 * @description 用自身事务
	 * 数据源: second据源
	 * @author qianye.zheng
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DataSource(name = DataSourceNames.SECOND)
	public void insertWithSelftTx()
	{
		/*
		 * 使用自身的事务，其他对象调用该方法时
		 * 该方法创建一个新事务，独立维护事务.
		 */
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来 insertWithSelftTx");
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
	 * @description 用自身事务
	 * 数据源: second据源
	 * 写多读少:主数据源加非只读事务。
	 * @author qianye.zheng
	 */
	@Transactional
	@DataSource(name = DataSourceNames.SECOND)
	public void writeManyReadLittle()
	{
		/*
		 * 写多读少:主数据源加非只读事务。
		 * 假设写很多，少量读取其他数据源
		 */
		CollegeStudent entity = new CollegeStudent();
		entity.setName("赵东来 writeManyReadLittle");
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
		
		/*
		 * readService.getFromFirst 需要加
		 * @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
			这样就不会受调用方的事务的影响，若不加该注解则数据源的切换实际上是没有生效的，
			因为受调用方写事务的影响.
		 */
		// 少量读取
		CollegeStudent entity2 = readService.getFromFirst(3);
		System.out.println(JacksonUtil.writeAsString(entity2));		
	}		
	
}
