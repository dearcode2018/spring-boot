/**
 * 描述: 
 * CoreDao.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.dao;

import java.io.Serializable;
import java.util.Collection;

import com.hua.entity.PagerEntity;

/**
 * 描述: 
 * 
 * @author qye.zheng
 * CoreDao
 */
public interface CoreDao<K extends Serializable, E> {

	/**
	 * 描述: 插入单个对象
	 * @author qye.zheng
	 * @param e
	 */
	public void insert(final E e);
	
	/**
	 * 注意: oracle与mysql等其他数据库方式是不同的
	 *这里实现的是oracle方式
	 * 描述: 批量插入对象
	 * @author qye.zheng
	 * @param collection
	 */
	public void insertBatch(final Collection<E> collection);
	
	/**
	 * 
	 * 描述: 删除单个对象
	 * @author qye.zheng
	 * @param id
	 */
	public void delete(final K id);
	
	/**
	 * 
	 * 描述: 批量删除对象
	 * @author qye.zheng
	 * @param ids
	 */
	public void deleteBatch(final K[] ids);
	
	/**
	 * 
	 * 描述: 更新单个对象(全量)
	 * 
	 * @author qye.zheng
	 * @param e
	 */
	public void update(final E e);
	
	/**
	 * 注意: oracle与mysql等其他数据库方式是不同的
	 *这里实现的是oracle方式
	 * 描述: 批量更新对象
	 * 
	 * @author qye.zheng
	 * @param collection
	 */
	public void updateBatch(final Collection<E> collection);
	
	/**
	 * 
	 * 描述: 获取单个对象 
	 * @author qye.zheng
	 * @param id
	 * @return
	 */
	public E get(final K id);
	
	/**
	 * 
	 * 描述: 统计个数 
	 * @author qye.zheng
	 * @param object 统计条件
	 * @return
	 */
	public Long count(final Object object);
	
	/**
	 * 
	 * 描述: 搜索符合条件的对象 
	 * @author qye.zheng
	 * @param pagerEntity
	 * @return
	 */
	public Collection<E> search(final PagerEntity pagerEntity);
	
	/**
	 * 
	 * 描述: 符合搜索条件的对象个数 
	 * @author qye.zheng
	 * @param pagerEntity
	 * @return
	 */
	public Long searchDataSize(final PagerEntity pagerEntity);
}
