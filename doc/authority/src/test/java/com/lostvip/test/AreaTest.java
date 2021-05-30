package com.lostvip.test;
     
    import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lostad.app.WebApplication;
import com.lostad.app.common.dao.JdbcDao;
import com.lostad.app.common.dao.impl.JpaDaoImpl;
import com.lostad.app.common.service.CommonService;
import com.lostad.app.system.entity.Area;
import com.lostad.app.system.entity.User;
      
    @RunWith(SpringJUnit4ClassRunner.class)  
    @SpringBootTest(classes=WebApplication.class)// 指定spring-boot的启动类  
    public class AreaTest {  
        @Autowired  
        private JdbcDao jdbcDao;  
        @Autowired
    	private CommonService commonService;
        @Test  
        public void list() throws Exception {  
        	
        	List list = jdbcDao.queryListBySql("select * from  t_task ");
        	System.out.println("======================="+list.size());
        	List<User> list2 = jdbcDao.queryListBySql("select * from  sys_user where id=? ",User.class,1);
        	System.out.println("======================="+list2.size());
        	
        	
        
        }
   }  


    