package com.erp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

//@ComponentScan(value = "com.erp")
//@ComponentScan(value = {"com.erp.daoImpl","com.erp.serviceImpl"})
// @EnableTransactionManagement // 开启事务管理
@MapperScan("com.**.dao")
//改造过程中先使用旧版本的xml配置文件（后面把XML改造成注解版之后会去掉这个注解）
//@ImportResource(locations = {"classpath:spring.xml"})
//使用hibernate的 sessionFactory ，不使用JPA的自动配置
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	//这里使用这种方式是因为项目会打成war包，部署到tomcat。
	//因为服务器上的tomcat做了https的配置，别的部门同事弄的我也不太懂，没时间研究先这么用着比较简单。
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

}
