package com.lostad.app.conf.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lostad.app.apiOpen.biz.entity.HisThirdParty;
import com.lostad.app.common.exception.ApiException;
import com.lostad.app.common.service.CacheService;
import com.lostad.app.common.util.ContextHolderUtils;
import com.lostad.app.common.util.RequestUtil;
import com.lostad.app.common.util.ResponseUtil;
import com.lostad.app.common.vo.JsonRe;
import com.lostad.app.security.util.SignUtil;

/**
 * 拦截所有非anno的请求
 * 
 * @author songsz
 *
 */
@Component
public class ApiRequestIntercepter implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CacheService cacheService;
	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		response.setHeader("Cache-Control", "no-cache"); 
		String requestPath = RequestUtil.getRequestPath(request);// 用户访问的资源地址
		HttpSession session = ContextHolderUtils.getSession();
		if (requestPath.startsWith("apiOpen/")) {//contentType 为json/application的请求，外部系统调用
			String signType = request.getHeader("signType");
			String appId = request.getHeader("appId");
			String sign = request.getHeader("sign");//
			logger.info("signType:"+signType+" appId:"+appId+" sign:"+sign);
			boolean signSuccess = false;
			try {
				if("MD5".equalsIgnoreCase(signType)){
					String contentType = request.getContentType();
					String data4Sign = null ;
					logger.debug(requestPath+"\n>>>>>>>>>>>>>> contentType:"+contentType);
					if(contentType.contains("application/json")){//json
						///data4Sign = IOUtils.toString(request.getInputStream(),"utf-8"); 不再支持json签名，无法与文件上传接口统一
					}else{//from
						data4Sign = SignUtil.getParam4Sign(request);
					}
					HisThirdParty p = cacheService.getHisThirdParty(appId);
					if(p==null){
						throw new ApiException("appId："+appId +"未注册！");
					}else{
						signSuccess = SignUtil.verifySignMD5(data4Sign,p.getSecretKey(),sign);
					}
				}else{
					throw new ApiException("签名方式未设置!");
				}
			} catch (ApiException e) {
				signSuccess = false;
				JsonRe re = new JsonRe(-1,e.getMessage(), null);
				ResponseUtil.flushJson(response, re);
		    }
			if(!signSuccess){
				JsonRe re = new JsonRe(-1,"签名认证失败！", null);
				ResponseUtil.flushJson(response, re);
			}
			return signSuccess;
		}else if(requestPath.startsWith("api/")){//未实现
			request.getRequestDispatcher("/preLogin").forward(request, response);
			return false;
		}else {
			
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		    String ctx =  request.getContextPath();
		    if(logger.isDebugEnabled()){
		    	logger.debug("===========ctx:"+ctx);
		    }
			request.setAttribute("ctx",ctx);
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}


}
