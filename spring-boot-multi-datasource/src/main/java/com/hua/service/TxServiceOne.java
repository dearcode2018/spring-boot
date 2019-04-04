/**
  * @filename TxServiceOne.java
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
 * @type TxServiceOne
 * @description 
 * @author qianye.zheng
 */
@Transactional
@Service
public class TxServiceOne
{
	
	@Resource
	private CollegeStudentMapper collegeStudentMapper;
	
	@Resource
	private TxServiceTwo txServiceTwo;
	
	
	/**
	 * 声明事务
	 * 
	 * 场景1: 
	 * 1.对象A方法调用对象B的方法，对象B的方法指定查询另外一个数据源
	 * 
	 * 场景2:
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * 数据源
	 * 1.指定主数据源
	 * 2.声明使用指定数据源，少数需要指定，大部分用主数据源即可
	 * 
	 * 设置主数据源: super.setDefaultTargetDataSource(defaultTargetDataSource);
	 * 
	 * 1.写多的工程: 
	 * 1) 写数据源作为主数据源，CoreService标注 @Transactional
	 * 2) 需要使用读数据源，类级或方法标注 @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true) 和
	 * @DataSource(name = DataSourceNames.READ)
	 * 	
	 * 
	 * 
	 * 2.读多工程
	 * 1) 读数据源作为主数据源，CoreService标注	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	 * 2) 需要使用写数据源，类级或方法标注 @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false) 和
	 * @DataSource(name = DataSourceNames.WRITE)
	 */
	
	
	
	
	
	
	/**
	 * 对象A方法调用对象B的方法，对象B的方法指定查询另外一个数据源
	 *  对象A中声明了事务，受默认的传播属性影响，对象B的方法也会使用同样的数据源和事务，
	 *  也就是此时对象B的方法声明的数据源不再起作用.
	 *  
	 *  若希望对象B的方法声明的数据源起作用，则看insertFirstGetFromSecond2()
	 *  
	 */
	/**
	 * 
	 * @description 
	 * 数据源: first据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	public void insertFirstGetFromSecond()
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
		
		/*
		 * 由于受当前方法事务的影响，getFromSecond 声明的数据源无效，还是使用
		 * 当前方法的数据源
		 */
		CollegeStudent entity2 = txServiceTwo.getFromSecond(3);
		System.out.println(JacksonUtil.writeAsString(entity2));
	}
	
	
	/*
	 * 	若希望对象B的方法声明的数据源起作用 则在对象B的方法中加上如下 声明
	 * @Transactional(propagation = Propagation.NOT_SUPPORTED) 不支持事务，因此上一个调用者的事务
	 * 边不会影响到当前方法的查询效果
	 * 
	 */
	/**
	 * 
	 * @description 
	 * 数据源: first据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	public void insertFirstGetFromSecond2()
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
		
		/*
		 * @Transactional(propagation = Propagation.NOT_SUPPORTED) 不支持事务，因此getFromSecond2
		 * 声明的数据源有效
		 */
		CollegeStudent entity2 = txServiceTwo.getFromSecond2(3);
		System.out.println(JacksonUtil.writeAsString(entity2));
	}	
	
	/*
	 * 	若希望对象B的方法声明的数据源起作用 则在对象B的方法中加上如下 声明
	 *@Transactional(propagation = Propagation.REQUIRES_NEW) 开启新事务，因此上一个调用者的事务
	 * 边不会影响到当前方法的查询效果
	 * 
	 */
	/**
	 * 
	 * @description 
	 * 数据源: first据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	public void insertFirstGetFromSecond3()
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
		/*
		 * @Transactional(propagation = Propagation.REQUIRES_NEW) 开启新事务，因此getFromSecond3
		 * 声明的数据源有效
		 */
		CollegeStudent entity2 = txServiceTwo.getFromSecond3(3);
		System.out.println(JacksonUtil.writeAsString(entity2));
	}		

	/**
	 * 
	 * @description 
	 * 数据源: first据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	public void insertFirstCallInsertSecond()
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
		
		/*
		 * 受当前方法事务传播的影响，insertToSecond声明的数据源无效
		 */
		txServiceTwo.insertToSecond();
	}	
	
	/**
	 * 
	 * @description 
	 * 数据源: first据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	public void insertFirstCallInsertSecond2()
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
		
		/*
		 * @Transactional(propagation = Propagation.REQUIRES_NEW) 开启新事务，因此insertToSecond2
		 * 声明的数据源有效
		 */
		txServiceTwo.insertToSecond2();
	}		
	
	/**
	 * 
	 * @description 
	 * 数据源: first据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.FIRST)
	public void insertFirstCallInsertSecond3()
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
		
		/*
		 * @Transactional(propagation = Propagation.NOT_SUPPORTED) 不支持事务，因此insertToSecond3
		 * 数据不持久化到数据库
		 */
		txServiceTwo.insertToSecond3();
	}		
	
	/**
	 * 
	 * @description 没有声明数据源和事务
	 * 使用主数据源和默认的事务(类级声明的)
	 * 数据源: second据源
	 * @author qianye.zheng
	 */
	public void insert()
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
	 * @author qianye.zheng
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@DataSource(name = DataSourceNames.FIRST)
	public void getCallInsert()
	{
		CollegeStudent entity = collegeStudentMapper.selectByPrimaryKey(3);
		System.out.println(JacksonUtil.writeAsString(entity));
		// 调用其他对象的方法
		txServiceTwo.insertToSecond();
	}
	
	/**
	 * 
	 * @description 查询第二个数据源，调用的方法必须声明为不支持事务或开启新的事务，然后声明自己要使用
	 * 的数据源，若使用同一个数据源则无需声明
	 * 1) 使用同一个数据源和同一个事务，则无需任何声明
	 * 2) 使用同一个数据源和不同的事务，则声明开启新的事务 @Transactional(propagation = Propagation.REQUIRES_NEW)
	 * 3) 使用不同的数据源的查询，声明为不支持事务@Transactional(propagation = Propagation.NOT_SUPPORTED) 
	 * 和 @DataSource(name = DataSourceNames.SECOND) 指定数据源
	 * 声明为不支持事务，主要是避免受调用方事务的影响
	 * 4) 使用不同的数据源需支持事务: 开启新的事务 @Transactional(propagation = Propagation.REQUIRES_NEW)
	 *  和 @DataSource(name = DataSourceNames.SECOND) 指定数据源
	 * 声明新事务，主要是避免受调用方事务的影响
	 * 
	 * 不支持事务或者开启新的事务 设置新的数据源才能生效，否则将受调用方事务传播的影响
	 * 
	 * 数据源: second 据源
	 * @author qianye.zheng
	 */
	@DataSource(name = DataSourceNames.SECOND)
	public void getFromSecondCallDefaultDataSource()
	{
		CollegeStudent entity = collegeStudentMapper.selectByPrimaryKey(3);
		System.out.println(JacksonUtil.writeAsString(entity));
		/*
		 * @Transactional(propagation = Propagation.NOT_SUPPORTED) 不支持事务，因此insertToSecond3
		 * 数据不持久化到数据库
		 */
		txServiceTwo.getFromDefaultDataSource();
	}		
	
}
