package com.jun.plugin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.plugin.service.UserService;

@Controller
@RequestMapping("/user")
public class Usercontroller {
    @Resource UserService userService;
    
    @RequestMapping("/login")
	public @ResponseBody Object invest(){
	     userService.updateUserinfo();
	     return "ok";
	}
    
    
          

}

