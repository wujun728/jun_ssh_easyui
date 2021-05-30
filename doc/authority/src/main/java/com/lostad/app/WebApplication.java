package com.lostad.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lostad.app.common.dao.impl.JpaDaoImpl;
import com.lostad.app.system.entity.Area;
import com.lostad.app.system.entity.Role;

@SpringBootApplication
public class WebApplication implements CommandLineRunner {
    @Autowired
	private JpaDaoImpl<Area,String> jpa;

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
	
		List list = jpa.findByProperty(Role.class, "roleCode", "administrator");
		System.out.println("========="+list);
//		if(list.size()==0){
//			Role r= new Role("超级管理员","administrator", "all","1");
//			jpa.save(r);
//		}
//		list = jpa.findByProperty(User.class, "username", "sa");
//		if(list.size()==0){
//			User r= new User();
//			r.setUsername("sa");
//			r.setPassword(MD5Utils.md5Hex("111111"));
//			r.setRealName("系统管理员");
//			r.setDelFlag(0);
//			jpa.save(r);
//		}
	}
}
