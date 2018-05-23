/**
 * 描述: 
 * PersonDaoImpl.java
 * @author qye.zheng
 * 
 *  version 1.0
 */
package com.hua.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hua.dao.PersonDao;
import com.hua.entity.Person;
import com.hua.mapper.PersonMapper;

/**
 * 描述: 
 * @author qye.zheng
 * 
 * PersonDaoImpl
 */
@Repository
public class PersonDaoImpl extends CoreDaoImpl<String, Person> implements
		PersonDao
{

	@Resource
	private PersonMapper mapper;
	
	/**
	 * 构造方法
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	public PersonDaoImpl(final PersonMapper mapper)
	{
		super(mapper);
		this.mapper = mapper;
	}
	
}
