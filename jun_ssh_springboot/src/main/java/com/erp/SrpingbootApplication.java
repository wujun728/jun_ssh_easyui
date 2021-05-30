package com.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class})
@ServletComponentScan
//@EntityScan(basePackages = {"com.erp.model","com.erp.entity"})
//@ComponentScan(basePackages = "com.erp")
public class SrpingbootApplication  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SrpingbootApplication.class);
    } 

    public static void main(String[] args) {
        SpringApplication.run(SrpingbootApplication.class, args);
    }
    
//    @Autowired
//    private EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public SessionFactory getSessionFactory() {
//        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
//            throw new NullPointerException("factory is not a hibernate factory");
//        }
//        return entityManagerFactory.unwrap(SessionFactory.class);
//    }
    
    
}