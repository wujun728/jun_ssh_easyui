package com.lostad.app.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lostad.app.common.dao.JdbcDao;
import com.lostad.app.common.dao.JpaDao;
import com.lostad.app.common.dao.impl.JpaDaoImpl;
import com.lostad.app.common.service.CommonService;
import com.lostad.app.common.util.SqlParamUtil;
import com.lostad.app.common.vo.Page;
import com.lostad.app.common.vo.SortDirection;

/**
 * 
 * @author sszvip
 * @param <T>
 *
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class CommonServiceImpl<T>  implements CommonService {
	@Autowired
	protected JdbcDao jdbcDao;
	@Autowired
	protected JpaDao jpaDao;
	/* 
	 * sql分页
	 */
	@Override
	public <T> Page datagridsql(String sql,Page pageParam, T paramObj, List<Object> params) throws Exception {
	
		StringBuffer sb_rows = new StringBuffer();
		StringBuffer sb_count = new StringBuffer();
		// 总量查询的SQL拼接
		sb_count.append("select count(1) from (");
		sb_count.append(sql);
		sb_count.append(") t where 1=1 ");
		// 记录查询的SQL拼接
		sb_rows.append("select * from (");
		sb_rows.append(sql);
		sb_rows.append(") t where 1=1 ");

		/// ssz改为下面的，以支持自定义注解
		StringBuffer sb_condition = SqlParamUtil.genSqlCondition(paramObj, params);
        sb_rows.append(sb_condition);
		sb_count.append(sb_condition);
		int count = jdbcDao.queryCountbySql(sb_count.toString(), params.toArray());
		/**
		 * 2、进行排序和分页的拼装
		 */
		// 排序字段 排序方式
		String sort = pageParam.getOrderBy();
		if (sort != null && !sort.equals("")) {
			sb_rows.append(" order by " + sort);
		}
		// 分页
		sb_rows.append(" limit ?,?");
		int pagesize = pageParam.getPageSize();
		int start = pageParam.getFirstResult();
		params.add(start);
		params.add(pagesize);

		List<? extends Object> list = jdbcDao.queryListBySql(sb_rows.toString(), paramObj.getClass(),params.toArray());

		Page page = new Page<>();
		page.setCount(count);
		page.setList(list);
		return page;
	}
	
	
	/* (non-Javadoc)
	 * @see com.lostad.app.common.service.impl.CommonService#queryListBySql(java.lang.String, java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> List<T> queryListBySql(String sql,Class<T> c,Object ... params){
		return jdbcDao.queryListBySql(sql, c, params);
	}
	
	/* (non-Javadoc)
	 * @see com.lostad.app.common.service.impl.CommonService#queryListBySql(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<Map<String,Object>> queryListBySql(String sql,Object ... params){
		return jdbcDao.queryListBySql(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see com.lostad.app.common.service.impl.CommonService#queryCountbySql(java.lang.String, java.lang.Object)
	 */
	@Override
	public int queryCountbySql(String sql, Object ... params){
		return jdbcDao.queryCountbySql(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see com.lostad.app.common.service.impl.CommonService#excuteSql(java.lang.String, java.lang.Object)
	 */
	@Override
	public int excuteSql(String sql,Object ... params){
		return jdbcDao.excuteSql(sql, params);
	}


	@Override
	public <T> void save(T entity) {
		jpaDao.save(entity);
	}


	@Override
	public <T> void batchSave(List<T> entitys) {
		jpaDao.batchSave(entitys);
	}


	@Override
	public <T> void merge(T entity) {
		jpaDao.merge(entity);
	}


	@Override
	public <T> void delete(T entity) {
		jpaDao.delete(entity);
	}


	@Override
	public <T> T find(Class<T> entity, Serializable id) {
		return jpaDao.find(entity, id);
	}


	@Override
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return jpaDao.findUniqueByProperty(entityClass, propertyName, value);
	}


	@Override
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return jpaDao.findByProperty(entityClass, propertyName, value);
	}


	@Override
	public <T> List<T> findHql(String hql, Object... args) {
		return jpaDao.findHql(hql, args);
	}


	@Override
	public <T> List<T> findHql(String hql, int index, int pageSize,
			Object... args) {
		return jpaDao.findHql(hql, index, pageSize, args);
	}
	
}
	
	
