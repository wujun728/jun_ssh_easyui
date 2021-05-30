package com.lostad.app.system.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public static boolean isNotEmpty(String v) {
		return v!=null && !"".equals(v.trim());
	}
	public static boolean isEmpty(String v) {
		return v==null || "".equals(v.trim());
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

