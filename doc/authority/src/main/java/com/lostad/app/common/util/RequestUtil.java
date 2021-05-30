package com.lostad.app.common.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	   /**
		 * 获得用户远程地址
		 */
		public static String getRemoteAddr(HttpServletRequest request){
			String remoteAddr = request.getHeader("X-Real-IP");
	        if (Validator.isNotEmpty(remoteAddr)) {
	        	remoteAddr = request.getHeader("X-Forwarded-For");
	        }else if (Validator.isNotEmpty(remoteAddr)) {
	        	remoteAddr = request.getHeader("Proxy-Client-IP");
	        }else if (Validator.isNotEmpty(remoteAddr)) {
	        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
	        }
	        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
		}
		
		public static String getRequestPath(HttpServletRequest request) {
			String requestPath = request.getRequestURI() + "?" + request.getQueryString();
			if (requestPath.indexOf("&") > -1) {// 去掉其他参数
				requestPath = requestPath.substring(0, requestPath.indexOf("&"));
			}
			requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
			return requestPath;
		}

}

