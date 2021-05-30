package com.lostad.app.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
  
    @GetMapping(value = "/welcome")
    public String listTask(HttpServletRequest request){
        //request.setAttribute("tasks", taskService.findAll());
        request.setAttribute("mode", "MODE_TASKS");
        return "/main/welcome";
    }
    
    @GetMapping(value = "/indexMain")
    public String indexMain(HttpServletRequest request){
        //request.setAttribute("tasks", taskService.findAll());
        request.setAttribute("mode", "MODE_TASKS");
        return "/main/indexMain";
    }
}
