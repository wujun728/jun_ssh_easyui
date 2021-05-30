package com.lostad.app.common.util;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**   
 * 类描述
 * @author：作者
 * @since ：2016年5月23日        
 */
public class ResponseUtil {
	// -----------------------------------------------------------------------------------
		// 以下各函数可以提成共用部件 (Add by Quainty)
		// -----------------------------------------------------------------------------------
		public static void flushJson(HttpServletResponse response, Object obj) {
			try {
				response.setContentType("application/json");
				response.setHeader("Cache-Control", "no-store");
				PrintWriter pw=response.getWriter();
///				Gson g = new Gson();
///				String json = g.toJson(obj);
				String json = JsonUtil.objectToJson(obj);
				pw.write(json);
				pw.flush();
				pw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 *方法的说明
		 * @param 
		 * @return void
		 */
		public static void sendRedirectToError(HttpServletRequest request,HttpServletResponse response,String msg) {
			try {
				 request.setAttribute("name",msg);
				 RequestDispatcher rd = request.getRequestDispatcher("webpage/system/error/error.jsp");
			     rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
}

