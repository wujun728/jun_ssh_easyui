package com.lostad.app.main.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lostad.app.common.service.CacheService;
import com.lostad.app.common.service.CommonService;
import com.lostad.app.common.util.CookieUtils;
import com.lostad.app.common.util.RequestUtil;
import com.lostad.app.common.util.StringUtils;
import com.lostad.app.system.entity.Area;
import com.lostad.app.system.entity.User;
import com.lostad.app.system.service.LoginService;
/**
 * 
 * @author sszvip
 *
 */
@Controller
public class IndexController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LoginService loginService;
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private CommonService commonService;
	
	
	@GetMapping(value={"","/preLogin"})
    public String preLogin(HttpServletRequest request){
        request.setAttribute("mode", "MODE_main");
       
        return "/login";
    }
	
	@GetMapping(value="/logout")
    public String logout(HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
        return "/login";
    }
	@RequestMapping(value = "/login", method = RequestMethod.POST)    
    public String login(HttpServletRequest request, 
    		@RequestParam String username,
			@RequestParam String password,
			@RequestParam(required=false) String vercode, //可以不传
			ModelMap model) {   
		HttpSession session = request.getSession();   
		String failNumKey = RequestUtil.getRemoteAddr((HttpServletRequest)request);
        Integer failNum = (Integer) session.getAttribute(failNumKey);
        System.out.println("sessionId:"+session.getId()+" =====/login==failNum:"+failNum);    
        String msg = "" ;
        try{
        	   //begin 验证码处理
               if(failNum==null){//
               	session.setAttribute(failNumKey, 1);
               }else if(failNum<15){//允许15次失败
               	session.setAttribute(failNumKey, ++failNum);
               }else{
                   String sessionCode = (String) session.getAttribute("validateCode");// 取出验证码    
                   String randomcode = request.getParameter("randomcode"); //提交的验证码   
                   //输入的验证码与session中的验证进行对比    
                   if (!sessionCode.equalsIgnoreCase(randomcode)) {    
                       throw new Exception("验证码不正确！");
                   }    
               }
               //end 验证码处理
               Subject subject = SecurityUtils.getSubject();
               ////subject.logout();//会话失效
  			   UsernamePasswordToken token = new UsernamePasswordToken(username, password);
  			   subject.login(token);
  			   subject.getPrincipal();
  			   return "redirect:/main/indexMain";
  		} catch (AuthenticationException e) {
  			msg = e.getMessage();
  		}catch(Exception e){
        	msg = e.getMessage();
        }
        model.put("message", msg);    
        return "/login";    
    }   
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	@GetMapping(value="/error")
    public String error1(HttpServletRequest request){
        request.setAttribute("msg", "something wrong!!");
        return "/common/error";
    }
    
}
