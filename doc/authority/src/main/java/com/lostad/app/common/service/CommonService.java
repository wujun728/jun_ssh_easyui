package com.lostad.app.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lostad.app.common.dao.JpaDao;
import com.lostad.app.common.vo.Page;
/**
 * 这个类是基于 jpa 和 jdbc 实现
 * @author songsz
 *
 */
public interface CommonService {

	/**
	 * 根据SQL查询单一结果
	 * 
	 * @param
	 * @return
	 */
	public abstract <T> Page datagridsql(String sql, Page pageParam,
			T paramObj, List<Object> params) throws Exception;

	public abstract <T> List<T> queryListBySql(String sql, Class<T> c,
			Object... params);

	public abstract List<Map<String, Object>> queryListBySql(String sql,
			Object... params);

	public abstract int queryCountbySql(String sql, Object... params);

	public abstract int excuteSql(String sql, Object... params);

	
	
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