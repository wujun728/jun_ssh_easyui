package com.lostad.app.common.controller;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.lostad.app.system.service.LoginService;
/**
 * 
 * @author sszvip
 *
 */
@Controller
public class VercodeController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LoginService loginService;
	
	@GetMapping(value={"/vercode"})
    public void genVercode(HttpServletRequest request,HttpServletResponse response){
		try {
	         int width = 60;  
	         int height = 32;  
	         //create the image  
	         BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
	         Graphics g = image.getGraphics();  
	         // set the background color  
	         g.setColor(new Color(0xDCDCDC));  
	         g.fillRect(0, 0, width, height);  
	         // draw the border  
	         g.setColor(Color.black);  
	         g.drawRect(0, 0, width - 1, height - 1);  
	         // create a random instance to generate the codes  
	         Random rdm = new Random();  
	         String hash1 = Integer.toHexString(rdm.nextInt());  
	         System.out.print(hash1);  
	         // make some confusion  
	         for (int i = 0; i < 50; i++) {  
	             int x = rdm.nextInt(width);  
	             int y = rdm.nextInt(height);  
	             g.drawOval(x, y, 0, 0);  
	         }  
	         // generate a random code  
	         String capstr = hash1.substring(0, 4);  
	         HttpSession session = request.getSession(true);  
	         //将生成的验证码存入session  
	         session.setAttribute("validateCode", capstr);  
	         g.setColor(new Color(0, 100, 0));  
	         g.setFont(new Font("Candara", Font.BOLD, 24));  
	         g.drawString(capstr, 8, 24);  
	         g.dispose();  
	         //输出图片  
	         response.setContentType("image/jpeg");  
	         OutputStream strm = response.getOutputStream();
	         ImageIO.write(image, "jpeg", strm);  
	         strm.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }
    
}
