package com.lostad.app.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public static boolean isNotEmpty(Object v) {
		if(v==null){
			return false;
		}else{
			return !"".equals(v.toString().trim());
		}
		
	}
	public static boolean isEmpty(Object v) {
		if(v==null){
			return true;
		}else{
			return  "".equals(v.toString().trim());
		}
		
	}
	
	public static boolean isNumeric(String str){ 
		boolean is = false ;
		if(isEmpty(str)){
			is = false;
		}else{
			is =  Pattern.compile("[0-9]*").matcher(str).matches(); 
		}
		
		return is;
	}
	
	public static void main(String[] a){
		System.out.println(isNumeric("ww"));
	}

}

