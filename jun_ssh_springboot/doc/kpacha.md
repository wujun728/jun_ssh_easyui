<dependency>
		    <groupId>com.github.penggle</groupId>
		    <artifactId>kaptcha</artifactId>
		    <version>2.3.2</version>
		    <exclusions>
		    	<exclusion>
		    		<groupId>javax.servlet</groupId>
		    		<artifactId>javax.servlet-api</artifactId>
		    	</exclusion>
		    </exclusions>
		</dependency>
		
//@ImportResource(locations = {"classpath:config/kaptcha.xml"})		
		
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
	<!-- 生成kaptcha的bean -->
	<bean id="captchaProducer"
		class="com.google.code.kaptcha.impl.DefaultKaptcha">
		<property name="config">
			<bean class="com.google.code.kaptcha.util.Config">
				<constructor-arg type="java.util.Properties">
					<!--设置kaptcha属性 -->
					<props>
						<prop key="kaptcha.border ">yes</prop>
						<prop key="kaptcha.border.color">105,179,90</prop>
						<prop key="kaptcha.textproducer.font.color">blue</prop>
						<prop key="kaptcha.image.width">100</prop>
						<prop key="kaptcha.image.height">50</prop>
						<prop key="kaptcha.textproducer.font.size">27</prop>
						<prop key="kaptcha.session.key">code</prop>
						<prop key="kaptcha.textproducer.char.length">4</prop>
						<prop key="kaptcha.textproducer.font.names">宋体,楷体,微软雅黑</prop>
						<prop key="kaptcha.textproducer.char.string">0123456789ABCEFGHIJKLMNOPQRSTUVWXYZ</prop>
						<prop key="kaptcha.obscurificator.impl">com.google.code.kaptcha.impl.WaterRipple</prop>
						<prop key="kaptcha.noise.color">black</prop>
						<prop key="kaptcha.noise.impl">com.google.code.kaptcha.impl.DefaultNoise</prop>
						<prop key="kaptcha.background.clear.from">185,56,213</prop>
						<prop key="kaptcha.background.clear.to">white</prop>
						<prop key="kaptcha.textproducer.char.space">3</prop>
					</props>
				</constructor-arg>
			</bean>
		</property>
	</bean>
</beans>	


package com.erp.configuration;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
//原文链接：https://blog.csdn.net/liunian02050328/article/details/53462053
@Configuration
public class CaptchaConfig {
 
	@Bean(name="captchaProducer")
	public DefaultKaptcha getKaptchaBean(){
		DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
		Properties properties=new Properties();
		properties.setProperty("kaptcha.border", "yes");
		properties.setProperty("kaptcha.border.color", "105,179,90");
		properties.setProperty("kaptcha.textproducer.font.color", "blue");
		properties.setProperty("kaptcha.image.width", "125");
		properties.setProperty("kaptcha.image.height", "45");
		properties.setProperty("kaptcha.session.key", "code");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");		
		Config config=new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}


<img style="width:85px;height:35px;margin-top: -10px;" align="absmiddle" id="Kaptcha" src="Kaptcha.jpg"/>

http://localhost:8081/erp/Kaptcha.jpg?35



	