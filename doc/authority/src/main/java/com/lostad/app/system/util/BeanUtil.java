package com.lostad.app.system.util;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 拷贝属性，直接对field进行操作
 * bean没有getter setter方法也可以进行拷贝
 * @author sszvip
 *
 */
public class BeanUtil {  

    public static void copyProperties(Object dist, Object src,boolean copyNull,boolean parentInclude){  
        Class classDist = dist.getClass();
        Class classSrc = src.getClass();  
       do{
    	   
    	   Field fields[] = classDist.getDeclaredFields();  
           
           for(Field distField :fields){  
               try {  
               	copyData(dist,distField,src,classSrc, copyNull,parentInclude);
               } catch (Exception e) {//如果本类没有，递归查找父类的  
               	e.printStackTrace();
               }  
           }//end for 
           
           classDist = classDist.getSuperclass();
          // System.out.println("==========================="+classDist.getSimpleName());
       }while(!"Object".equals(classDist.getSimpleName()) && parentInclude);
        
    }

	/**
	 * 同名属性复制
	 *
	 * @param  dist    目标对象
	 * @param  dist    目标Field
	 * @param  dist    数据源对象
	 * @param  dist    数据源对象的class或超类的class
	 * @return boolean 是否复制null
	 */
	private static void copyData(Object distObj,Field distField,Object srcObj,Class srcClass,boolean copyNull,boolean includeParent) {
		Object srcValue = null;
		try{
			distField.setAccessible(true);//
			
		    String fieldName = distField.getName();
		    if("serialVersionUID".equals(fieldName)){
		    	return ;
		    }
		    Field srcField = srcClass.getDeclaredField(fieldName);//本类的 field
		    srcField.setAccessible(true);
		    
			srcValue = srcField.get(srcObj);
			//System.out.println(srcField.getName()+"====copyData======"+srcValue);
			//复制
			if(srcValue==null){
				if(copyNull){
					distField.set(distObj, srcValue);//强制赋值给dist
				}
			}else{
				distField.set(distObj, srcValue);//强制赋值给dist
			}
		}catch(Exception e){
			srcClass = srcClass.getSuperclass();
			if(includeParent && !"Object".equals(srcClass.getSimpleName())){//
				copyData(distObj, distField, srcObj, srcClass, copyNull, includeParent);
			}else{
				return ;
			}
		}
	}  
	
	
	public static Object getValue(Object srcObj,Field srcField) {
		Object srcValue = null;
		try{
		    srcField.setAccessible(true);
			srcValue = srcField.get(srcObj);
		}catch(Exception e){
			
		}
		return srcValue;
	}  
	public static void setValue(Object obj,Field srcField,Object value) {
		try{
		    srcField.setAccessible(true);
			srcField.set(obj, value);
		}catch(Exception e){
			
		}
	}  
	
  
	public static void main(String[] args){
//		Test doc = new Test();
//		doc.setTest("test");
//		doc.setRealName("123");
//		
//		Test doc2 = new Test();
//		
//		BeanUtil.copyProperties(doc2, doc, true, true);
//		Object d = new Object();
	}
}  