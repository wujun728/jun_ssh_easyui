/**
 * Copyright &copy; 2015-2020 <a href="http://www.lostad.org/">JeePlus</a> All rights reserved.
 */
package com.lostad.app.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 * 必须与Spring Boot启动类同包或其子包
 * @author sszvip@qq.com
 * @date   20170609
 */
@Component
public class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;
	private static Logger logger = LoggerFactory.getLogger(SpringUtil.class);

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)throws BeansException {
		if (applicationContext == null) {
			applicationContext = ctx;
		}
		logger.info("+++++++++++++++++++++SpringContextHolder　初始化成功！！！"+applicationContext);
	}
}