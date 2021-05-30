package com.lostad.app.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

/**
 * 
 * 类描述：DAO层泛型基类接口
 * 
 * 张代浩
 * 
 * @date： 日期：2012-12-8 时间：下午05:37:33
 * @version 1.0
 */
public interface JpaDao {
	/**
	 * 获取所有数据库表
	 * 
	 * @return
	 */
	public <T> void save(T entity);
	public <T> void batchSave(List<T> entitys);
	public <T> void merge(T entity); 
	/**
	 * 删除实体
	 * @param entitie
	 */
	public <T> void delete(T entity);

	/**
	 * 根据实体名称和主键获取实体
	 * 
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @return
	 */
	public <T> T find(Class<T> entity, Serializable id);

	/**
	 * 根据实体名字获取唯一记录
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value);

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value);
	public <T> List<T> findHql(String hql,Object ... args);
	public <T> List<T> findHql(String hql,int index,int pageSize,Object ... args);
}
