/**   
 * 类描述
 * @author：作者
 * @since ：2016年11月1日        
 */

package com.lostad.app.common.util;

import java.lang.reflect.Field;
import java.util.List;

import com.lostad.app.common.anotation.SqlParamGroup;


/** 
 * 解析vo里面的自定义注解
 * @author：sszvip@qq.com
 * @since ：2016年11月1日        
 */
public class SqlParamUtil {
	public static StringBuffer genSqlCondition(Object entity,List<Object> params){
		StringBuffer sb = new StringBuffer();
		try{
			Field[] field_arr =entity.getClass().getDeclaredFields();
		
			for (Field field : field_arr) {
				//获取自定义的注解
				SqlParamGroup annoGroup = field.getAnnotation(SqlParamGroup.class);  
				 if(annoGroup!=null){//有注解使用注解的列名
					 String condition = getConditionWithAnno(entity,field);
					 if(Validator.isNotEmpty(condition)){
						 sb.append(condition);
					 }
				}else{//使用属性名做为列名(继续走原来的方法)
					String con = getConditionNoAnno(entity,field,params);
					if(Validator.isNotEmpty(con)){
						 sb.append(con);
					 }
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * 有自定义SqlParamGroup注解的属性
	 * @param entity 
	 * @param 
	 * @return Object
	 */
	private static String getConditionWithAnno(Object entity, Field field) {
		Object value = BeanUtil.getValue(entity,field);
		if(value==null || value.toString().equals("") ){
			return null;
		}
		field.setAccessible(true);
		
		StringBuilder sb = new StringBuilder();
		SqlParamGroup annoGroup = field.getAnnotation(SqlParamGroup.class);  
		String colName = annoGroup.colName();
		String oper    = annoGroup.operation();
		String formatTo    = annoGroup.formatTo();
		if(Validator.isEmpty(colName)){
			colName = field.getName();
		}
		
		if(Validator.isNotEmpty(formatTo)){
			if(value instanceof String){
				value = transactSQLInjection(value.toString());//字符串类型的值，去掉非法字符
				value = String.format(formatTo, value.toString());
			}else if(value instanceof Integer){
				value = String.format(formatTo,(Integer)value);
			}else if(value instanceof Float){
				value = String.format(formatTo,(Float)value);
			}else if(value instanceof Double){
				value = String.format(formatTo,(Double)value);
			}
		}
		
		sb.append(" and ");
		if("like".equals(oper)){//字符串
			value = "'%"+value.toString().trim()+"%'";
			sb.append(colName).append(" like ").append(value);// age >= ?
		}else{//字符串或数字等其它类型
			sb.append(colName).append(" ").append(oper).append(" ").append(value);// age >= ?
		}
		
		BeanUtil.setValue(entity,field,null);//清空此属性防止其它地方重复拼入sql
		return sb.toString();
	}

	/**
	 * 没有自定义SqlParamGroup注解的属性
	 * @param 
	 * @return Object
	 */
	private static String getConditionNoAnno(Object entity,Field field,List params) {
	
		Object value = BeanUtil.getValue(entity,field);
		if(value==null || value.toString().equals("") ){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		//属性的类型名
		String typeName = field.getType().getCanonicalName();
		String condition = null ;
		
		if (typeName.equals("java.lang.String")) {
			condition = "like";
			params.add("%" + value.toString().trim() + "%");
		} else {
			condition = "=";
			params.add(value);
		}
		
		sb.append(" and ");
		sb.append(field.getName());
		sb.append(" ");
		sb.append(condition);
		sb.append(" ? ");
		return sb.toString();
	}
	 /**
	  * 去掉字符串类型变量中的非法字符，防止注入
	  * @param 
	  * @return String
	  */
	 public static String transactSQLInjection(String sql) { 
	   return sql.replaceAll(".*([';]+|(--)+).*", " "); 
     } 
}

