package com.yc;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyLog2 {
	@Pointcut("execution(* com.yc.*.add*(..)) || execution(* com.yc.*.del*(..))")
	public void yy(){
		
	}
	/**
	 * ֪ͨ
	 */
	@After("yy()")
	public void log(){
		System.out.println("����֪ͨ");
	}
	
}
