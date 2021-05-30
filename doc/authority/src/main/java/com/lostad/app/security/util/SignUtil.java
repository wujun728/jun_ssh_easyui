package com.lostad.app.security.util;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * 
 * @author waters
 * 
 */

public class SignUtil {

    /**
     *对字符串签名
     * @param 
     * @return String
     */
	public static String signMD5(String srcStr,String appsecret) {
		String dist = null ;//签名
		String ds = "appsecret"+srcStr+appsecret;
		//System.out.println("---------------ds未加密"+ds);
		try {
			dist = DigestUtils.md5Hex(ds.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//System.out.println("- ----ds加密"+dist.toUpperCase());
		return dist.toUpperCase();//签名是大写的
	}
	
	//sign对业务数据的签名 
	public static boolean verifySignMD5(String src, String appKey,String sign){
		boolean isOk = false;
		try{
			String signNew = signMD5(src,appKey);
		if(signNew.equals(sign)){
			isOk = true;
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return isOk;
	}
	
	
	/**
	 * 
	 * 获取request中的参数并组合成一个字符串，如：
	 * id=1234&name=小明&age=16
	 * @param 
	 * @return String
	 */
	public static String getParam4Sign(HttpServletRequest request) {
		List<String> paramList = new ArrayList<String>();
		//方式一：getParameterMap()，获得请求参数map  
        Map<String,String[]> map= request.getParameterMap();  
        //参数迭代器  
        Iterator<String> iterator = map.keySet().iterator();  
        while(iterator.hasNext()){  
            String k=iterator.next();  
            paramList.add(k+"="+ map.get(k)[0]);
        }  
        Object[] params = paramList.toArray();
        Arrays.sort(params);
        
        StringBuilder sb = new StringBuilder();
        for(Object param:params){
        	sb.append(param);
        }
		return sb.toString();
	}

	public static String sign(HttpServletRequest request, String... strings) {
		List<String> paramList = new ArrayList<String>();
		for (String s : strings) {
			paramList.add(s);
		}
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			paramList.add(parameterName);
		}
		Object[] paramArray = paramList.toArray();
		Arrays.sort(paramArray);
		StringBuffer sb = new StringBuffer(500);
		for (Object obj : paramArray) {
			if (obj != null) {
				sb.append(obj.toString());
				if (request.getParameter(obj.toString()) == null) {
					sb.append(request.getHeader(obj.toString()));
				} else {
					sb.append(request.getParameter(obj.toString()));
				}
			}
		}
		return sb.toString();
	}

}