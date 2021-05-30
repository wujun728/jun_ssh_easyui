package com.lostad.app.common.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lostad.app.common.dao.JpaDao;

/**
 * 类描述： DAO层泛型基类
 * 
 * sszvip@qq.com
 * 
 * @date： 日期：2017-06-11 
 * @param <T>
 * @param <PK>
 * @version 1.0
 */
@SuppressWarnings("hiding")
@Repository
public class JpaDaoImpl<T, PK extends Serializable> implements JpaDao {
	/**
	 * 初始化Log4j的一个实例
	 */
	private static final Logger logger = Logger.getLogger(JpaDaoImpl.class);
	/**
	 * 如果只有一个<persistence-unit>，不需要明确指定unitName。
	 * 持久化实体persist()：往数据表中插入数据。
     * 删除实体remove()：从数据表中删除记录。
     * 更新实体merge()：更新数据表记录。
     * find(entityName, id)
	 **/
///	@PersistenceContext(unitName="em")
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * 批量保存数据
	 * 
	 * @param <T>
	 * @param entitys
	 * 要持久化的临时实体对象集合
	 */
	public <T> void batchSave(List<T> entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			em.persist(entitys.get(i));
			if (i % 20 == 0) {
				// 20个对象后才清理缓存，写入数据库
				em.flush();
				em.clear();
			}
		}
		// 最后清理一下----防止大于20小于40的不保存
		em.flush();
		em.clear();
	}
	
	/**
	 * 
	 * 如果已经存在持久化
	 * @param 
	 * @return void
	 */
	public <T> void merge(T entity) {
		try {
			em.merge(entity);
			em.flush();
			if (logger.isDebugEnabled()) {
				logger.debug("添加或更新成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("添加或更新异常", e);
			throw e;
		}
	}

	
	/**
	 * 根据传入的实体删除对象
	 */
	public <T> void delete(T entity) {
		try {
			em.remove(entity);
			em.flush();
			if (logger.isDebugEnabled()) {
				logger.debug("删除成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("删除异常", e);
			throw e;
		}
	}


	
	public List<T> find(String hql, Object... args) {  
		List<T> list = null ;
        try {  
            Query query = em.createQuery(hql);  
            if(args!=null){
            	for (int i = 0; i < args.length; i++) {  
                    query.setParameter(i + 1, args[i]);  
                }  
            }
            
            list = query.getResultList();  
            if (logger.isDebugEnabled()) {  
                logger.debug(list.size()+" hql查询:" +hql);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
        return list;  
    }
	@Override
	public <T> void save(T entity) {
		try {
			em.persist(entity);
			em.flush();
			if (logger.isDebugEnabled()) {
				logger.debug("添加或更新成功," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("添加或更新异常", e);
			throw e;
		}
	}

	@Override
	public <T> T find(Class<T> entityClass, Serializable id) {
		T t = null;
		try {
			t = em.find(entityClass, id);
			em.flush();
			if (logger.isDebugEnabled()) {
				logger.debug("添加或更新成功," + entityClass.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("添加或更新异常", e);
			throw e;
		}
		return t;
	}

	@Override
	public <T> T findUniqueByProperty(Class<T> entityClass,String propertyName, Object value) {
		try{
			List<T> list = findByProperty( entityClass, propertyName,  value);
			if(list.size()>1){
				throw new RuntimeException("结果集非唯一！！");
			}else if(list.size()==1){
				return list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> List<T> findByProperty(Class<T> entityClass,String propertyName, Object value) {
		List<T> list = null ;
		final String hql = " from "+entityClass.getCanonicalName()+" where "+ propertyName + "= ? ";
		try{
			Query query = em.createQuery(hql);  
	        query.setParameter(1, value);  
	        list = query.getResultList();  
	        if (logger.isDebugEnabled()) {  
	            logger.debug(list.size()+" hql查询:" +hql);  
	        }  
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("查询异常！"+hql);
		}
		return list;
	}

	@Override
	public <T> List<T> findHql(String hql, Object... args) {
		Query q = em.createQuery(hql);
		if(args!=null){
			for(int i=0;i<args.length;i++){
				q.setParameter(i+1, args[i]);
			}
		}
		return q.getResultList();
	}

	@Override
	public <T> List<T> findHql(String hql, int index, int pageSize,Object... args) {
		Query q = em.createQuery(hql);
		if(args!=null){
			for(int i=0;i<args.length;i++){
				q.setParameter(i+1, args[i]);
			}
		}
		q.setFirstResult(index);
		q.setMaxResults(pageSize);
		
		return q.getResultList();
	}

}
