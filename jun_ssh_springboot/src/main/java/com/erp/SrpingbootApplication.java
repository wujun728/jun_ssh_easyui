package com.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan
//@ImportResource(locations = {"classpath:config/kaptcha.xml"})
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
    
}