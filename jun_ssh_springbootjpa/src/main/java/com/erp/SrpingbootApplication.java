package com.erp;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@SpringBootApplication(
//	    exclude = {DataSourceAutoConfiguration.class,
//	       DataSourceTransactionManagerAutoConfiguration.class,
//	       MybatisAutoConfiguration.class}) 
@ComponentScan(basePackages = "com.erp")
public class SrpingbootApplication  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SrpingbootApplication.class);
    } 

    public static void main(String[] args) {
        SpringApplication.run(SrpingbootApplication.class, args);
    }
    
 
//	@Bean
//	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
//	    return hemf.getSessionFactory();
//	}
}