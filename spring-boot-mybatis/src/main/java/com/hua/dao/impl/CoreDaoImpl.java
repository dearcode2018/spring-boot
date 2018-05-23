/**
 * 描述: 
 * CoreDaoImpl.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.dao.impl;

import java.io.Serializable;
import java.util.Collection;

import com.hua.dao.CoreDao;
import com.hua.entity.PagerEntity;
import com.hua.log.BaseLog;
import com.hua.mapper.CoreMapper;

/**
 * 描述: 
 * 
 * @author qye.zheng
 * CoreDaoImpl
 */
public class CoreDaoImpl<K extends Serializable, E> extends BaseLog implements CoreDao<K, E> {
	
	private CoreMapper<K, E> coreMapper;
	
	/**
	 * 构造方法
	 * 描述: 
	 * @author  qye.zheng
	 */
	public CoreDaoImpl(final CoreMapper<K, E> coreMapper)
	{
		this.coreMapper = coreMapper;
	}
	
	/**
	 * 描述: 插入单个对象
	 * @author qye.zheng
	 * @param e
	 */
	@Override
	public void insert(final E e)
	{
		coreMapper.insert(e);
	}
	
	/**
	 * 注意: oracle与mysql等其他数据库方式是不同的
	 *这里实现的是oracle方式
	 * 描述: 批量插入对象
	 * @author qye.zheng
	 * @param collection
	 */
	@Override
	public void insertBatch(final Collection<E> collection)
	{
		coreMapper.insertBatch(collection);
	}
	
	/**
	 * 
	 * 描述: 删除单个对象
	 * @author qye.zheng
	 * @param id
	 */
	@Override
	public void delete(final K id)
	{
		coreMapper.delete(id);
	}
	
	/**
	 * 
	 * 描述: 批量删除对象
	 * @author qye.zheng
	 * @param ids
	 */
	@Override
	public void deleteBatch(final K[] ids)
	{
		coreMapper.deleteBatch(ids);
	}
	
	/**
	 * 
	 * 描述: 更新单个对象(全量)
	 * 
	 * @author qye.zheng
	 * @param e
	 */
	@Override
	public void update(final E e)
	{
		coreMapper.update(e);
	}
	
	/**
	 * 注意: oracle与mysql等其他数据库方式是不同的
	 *这里实现的是oracle方式
	 * 描述: 批量更新对象
	 * 
	 * @author qye.zheng
	 * @param collection
	 */
	@Override
	public void updateBatch(final Collection<E> collection)
	{
		coreMapper.updateBatch(collection);
	}
	
	/**
	 * 
	 * 描述: 获取单个对象 
	 * @author qye.zheng
	 * @param id
	 * @return
	 */
	@Override
	public E get(final K id)
	{
		return coreMapper.get(id);
	}
	
	/**
	 * 
	 * 描述: 统计个数 
	 * @author qye.zheng
	 * @param object 统计条件
	 * @return
	 */
	@Override
	public Long count(final Object object)
	{
		return coreMapper.count(object);
	}
	
	/**
	 * 
	 * 描述: 搜索符合条件的对象 
	 * @author qye.zheng
	 * @param pagerEntity
	 * @return
	 */
	@Override
	public Collection<E> search(final PagerEntity pagerEntity)	
	{
		return coreMapper.search(pagerEntity);
	}
	
	/**
	 * 
	 * 描述: 符合搜索条件的对象个数 
	 * @author qye.zheng
	 * @param pagerEntity
	 * @return
	 */
	@Override
	public Long searchDataSize(final PagerEntity pagerEntity)
	{
		return coreMapper.searchDataSize(pagerEntity);
	}

}
