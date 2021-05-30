/**
 * 
 */
package com.lostad.app.common.dao;

import java.util.List;
import java.util.Map;

/**
 * 使用sql进行复杂查询
 * @author sszvip
 *
 */
public interface JdbcDao {
	public <T> List<T> queryListBySql(String sql,Class<T> c,Object ... params);
	public  <T> List<T> queryListBySql(String sql,Object ... params);
	public int queryCountbySql(String sql, Object ... params);
	public int excuteSql(String sql,Object ... params);
}
