package com.erp.action;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.erp.dto.Json;
import com.erp.service.LoginService;
import com.erp.shiro.CaptchaUsernamePasswordToken;
import com.erp.shiro.IncorrectCaptchaException;
//import com.google.code.kaptcha.Constants;
import com.erp.util.Constants;
import com.google.code.kaptcha.Producer;

import lombok.extern.slf4j.Slf4j;

//@Action(value = "systemAction", results = { @Result(name = Constants.LOGIN_SUCCESS_URL, location = "/index.jsp"),
//		@Result(name = Constants.LOGIN_URL,location = "/login.jsp"),
//		@Result(name = Constants.LOGIN_LOGIN_OUT_URL,type="redirect",location = "systemAction!loginInit.action")})
@Slf4j
@Controller
@RequestMapping("/system")
public class LoginAction extends BaseAction
{	
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	private static final long	serialVersionUID	= -6019556530071263499L;
	private String userName;
	private String password;
	private String captcha;
	private String userMacAddr;
	private String userKey;
	
	public String getUserKey()
	{
		return userKey;
	}
	public void setUserKey(String userKey )
	{
		this.userKey = userKey;
	}
	public String getUserMacAddr()
	{
		return userMacAddr;
	}
	public void setUserMacAddr(String userMacAddr )
	{
		this.userMacAddr = userMacAddr;
	}
	private LoginService loginService;
	
	public String getCaptcha()
	{
		return captcha;
	}
	public void setCaptcha(String captcha )
	{
		this.captcha = captcha;
	}
	public LoginService getLoginService()
	{
		return loginService;
	}
	@Autowired
	public void setLoginService(LoginService loginService)
	{
		this.loginService = loginService;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
		
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password )
	{
		this.password = password;
	}

	@ResponseBody
	@PostMapping(value = "/load")
	public String load() throws Exception
	{	
		Subject subject=SecurityUtils.getSubject();
		CaptchaUsernamePasswordToken token=new CaptchaUsernamePasswordToken();
        token.setUsername(userName);
        token.setPassword(password.toCharArray());
        token.setCaptcha(captcha);
        token.setRememberMe(true);
        Json json=new Json();
        json.setTitle("登录提示");
        try{
            subject.login(token);
            System.out.println("sessionTimeout===>"+subject.getSession().getTimeout());
            json.setStatus(true);	
        }
        catch (UnknownSessionException use) {
            subject = new Subject.Builder().buildSubject();
            subject.login(token);
            logger.error(Constants.UNKNOWN_SESSION_EXCEPTION);
            json.setMessage(Constants.UNKNOWN_SESSION_EXCEPTION);
        }
        catch(UnknownAccountException ex){
			logger.error(Constants.UNKNOWN_ACCOUNT_EXCEPTION);
			json.setMessage(Constants.UNKNOWN_ACCOUNT_EXCEPTION);
		}
        catch (IncorrectCredentialsException ice) {
            json.setMessage(Constants.INCORRECT_CREDENTIALS_EXCEPTION);
        } 
        catch (LockedAccountException lae) {
            json.setMessage(Constants.LOCKED_ACCOUNT_EXCEPTION);
        }catch (IncorrectCaptchaException e) {
        	 json.setMessage(Constants.INCORRECT_CAPTCHA_EXCEPTION);
		}
        catch (AuthenticationException ae) {
            json.setMessage(Constants.AUTHENTICATION_EXCEPTION);
        } 
        catch(Exception e){
            json.setMessage(Constants.UNKNOWN_EXCEPTION);
        }
        OutputJson(json,Constants.TEXT_TYPE_PLAIN);
        //token.clear();
		return null;
	}
	/**  
	* 函数功能说明 TODO:用户登出
	* Administrator修改者名字
	* 2013-5-9修改日期
	* 修改内容
	* @Title: logout 
	* @Description:
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/

	@ResponseBody
	@GetMapping(value = "/logout")
	public String logout() throws Exception
	{
		SecurityUtils.getSubject().logout();
		Json json=new Json();
		json.setStatus(true);
		OutputJson(json);
		return null;
	}
	/**  
	* 函数功能说明 TODO:查询用户所有权限菜单
	* Administrator修改者名字
	* 2013-5-9修改日期
	* 修改内容
	* @Title: findAllFunctionList 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/

	@ResponseBody
	@GetMapping(value = "/findAllFunctionList")
	public String findAllFunctionList() throws Exception
	{
		OutputJson(loginService.findMenuList());
		return null;
	}
	
}
