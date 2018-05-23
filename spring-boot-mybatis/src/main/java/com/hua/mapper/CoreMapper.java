/**
* CoreMapper.java
* 
* @author qye.zheng
* 	version 1.0
 */
package com.hua.mapper;

import java.io.Serializable;
import java.util.Collection;

import com.hua.entity.PagerEntity;

/**
 * 描述: 核心 Mapper
 * @author qye.zheng
 K - primary key 主键
 E - entity 实体
 * CoreMapper
 */
public interface CoreMapper<K extends Serializable, E> {

	/**
	 * 接口和xml文件是相对独立的，2者之间有一个不对应存在
	 * 并不影响其启动，只要不通过mapper接口去调用即可，没有调用就
	 * 不会触发错误
	 * 
	 * 接口有，但是xml文件没有，则抛 找不到异常;
	 * 接口有，但是xml文件没有实现，则抛 无效sql类型异常
	 * 
	 * 接口没有，xml文件有，不会引发错误.
	 * 
	 */
	
	/**
	 * 描述: 插入单个对象
	 * @author qye.zheng
	 * @param e
	 */
	public int insert(final E e);
	
	/**
	 * 注意: oracle与mysql等其他数据库方式是不同的
	 *这里实现的是oracle方式
	 * 描述: 批量插入对象
	 * @author qye.zheng
	 * @param collection
	 */
	public int insertBatch(final Collection<E> collection);
	
	/**
	 * 
	 * 描述: 删除单个对象
	 * @author qye.zheng
	 * @param id
	 */
	public int delete(final K id);
	
	/**
	 * 
	 * 描述: 批量删除对象
	 * @author qye.zheng
	 * @param ids
	 */
	public int deleteBatch(final K[] ids);
	
	/**
	 * 
	 * 描述: 更新单个对象(全量)
	 * 
	 * @author qye.zheng
	 * @param e
	 */
	public int update(final E e);
	
	/**
	 * 注意: oracle与mysql等其他数据库方式是不同的
	 *这里实现的是oracle方式
	 * 描述: 批量更新对象
	 * 
	 * @author qye.zheng
	 * @param collection
	 */
	public int updateBatch(final Collection<E> collection);
	
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
