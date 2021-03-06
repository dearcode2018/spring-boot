/**
 * 描述: 
 * Person.java
 * @author qye.zheng
 * 
 *  version 1.0
 */
package com.hua.entity;

import java.util.Date;

/**
 * 描述: 
 * @author qye.zheng
 * 
 * Person
 */
public final class Person extends BaseEntity
{

	private static final long serialVersionUID = -8454893187455101231L;

	/**
	 * 一个人只有一个身份证，一个身份证只属于一个人
	 */
	
	/* 姓名 */
	private String name;
	
	/* 证件照片url */
	private String photoUrl;
	
	/* 民族 */
	private String nation;
	
	/* 出生日期 yyyy-MM-dd */
	private Date birthday;
	
	/* 住址 */
	private String address;
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl()
	{
		return photoUrl;
	}

	/**
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl)
	{
		this.photoUrl = photoUrl;
	}

	/**
	 * @return the nation
	 */
	public String getNation()
	{
		return nation;
	}

	/**
	 * @param nation the nation to set
	 */
	public void setNation(String nation)
	{
		this.nation = nation;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday()
	{
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
	
}
