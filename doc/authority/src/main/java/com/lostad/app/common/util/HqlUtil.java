package com.lostad.app.common.util;

import java.lang.reflect.Field;
import java.util.List;

import com.lostad.app.system.entity.Role;


/**   
 * @author：sszvip@qq.com
 * @since ：2017年6月27日        
 */
public class HqlUtil {

	/**
	 * 生成查询条件和where条件
	 * @param  strFields 要查询的对象属性
	 * @return void
	 */
	public static void genFieldAndWhere(final StringBuilder fields,final StringBuilder where,final String strFields) {
		if(Validator.isNotEmpty(strFields)){//
			fields.append(" select new Map(");
			String[] arr = strFields.split(",");
			for(int i=0;i<arr.length;i++){
				if(i==0){
					fields.append(arr[i]).append(" as ").append(arr[i]);
				}else{
					fields.append(",").append(arr[i]).append(" as ").append(arr[i]);
				}
				
				where.append(" and ").append(arr[i]).append(" !=0 ")
				     .append(" and ").append(arr[i]).append(" is not null ");
			}
			fields.append(") ");
		}
	}
	/**
	 * 组装HQL的查询条件
	 * @param whereCon  条件
	 * @param params    值 
	 * @param paramsSrc 源参数
	 */
	public static void genWhereCondition(final StringBuilder whereCon,final List<Object> params, Object paramsSrc) {
		if(paramsSrc!=null){//
		       Class<?> classSrc = paramsSrc.getClass();  
		       do{
		    	   Field fields[] = classSrc.getDeclaredFields();  
		           
		           for(int i=0;i<fields.length;i++){
		        	   Field f = fields[i];
		        	   f.setAccessible(true);
		        	   String name  = f.getName();
		        	   Object value;
		        	   try {
							value = f.get(paramsSrc);
							if(value==null){
								continue;
							}
							//只处理存在值的参数
			        		whereCon.append(" and ").append( name ).append(" =? ");
		        	        params.add(value);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
		           }//end for 
		           
		           classSrc = classSrc.getSuperclass();//递归父类的属性值
		       }while(!"Object".equals(classSrc.getSimpleName()));
		        
		    }
		}
}
