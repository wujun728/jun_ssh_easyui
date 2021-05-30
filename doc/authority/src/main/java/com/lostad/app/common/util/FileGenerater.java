package com.lostad.app.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lostad.app.common.exception.ApiException;
import com.lostad.app.conf.AppProperties;

/**
 * 
 * @author ：sszvip@qq.com
 * @since  ：2016年5月24日
 */
@Component
public class FileGenerater {
	private static final Logger logger = Logger.getLogger(FileGenerater.class);
	private static final String FILE_FORMAT_UPLOAD = "yyyyMMddHHmmss";//上传文件名中的文件格式
	@Autowired
	private AppProperties app;
	
	public String getAppRealPath(){
    	ServletContext ctx =  ContextHolderUtils.getRequest().getServletContext();
		String path = ctx.getRealPath("/");
		logger.info("his Path:"+path);
		return path;
	}

	/* 
	 * Java文件操作 获取文件扩展名 
	 * 
	 *  Created on: 2011-8-2 
	 *      Author: blueeagle 
	 */  
	    public String getExtensionName(String filename) {   
	        if ((filename != null) && (filename.length() > 0)) {   
	            int dot = filename.lastIndexOf('.');   
	            if ((dot >-1) && (dot < (filename.length() - 1))) {   
	                return filename.substring(dot + 1);   
	            }   
	        }   
	        
	        return filename;   
	    }   
	    
	/**
	 * 
	 *方法的说明
	 * @param ext     文件后缀名
	 * @return String
	 */
	public String createFileNameWidthDate(String patIdcard,String doctUsername,String fileType,String typecode,String ext,Date date) {
		StringBuilder name = new StringBuilder() ;
		try {
			//生成文件名
			if(date==null){
				date=new Date();
			}
			SimpleDateFormat format=new SimpleDateFormat(FILE_FORMAT_UPLOAD);
			String time=format.format(date);
			name.append(patIdcard).append("_").append(doctUsername).append("_").append(fileType).append("_").append(typecode).append("_");
			name.append(time).append(".").append(ext);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return name.toString();
	}
	
	/**
	 * 
	 *方法的说明
	 * @param ext     文件后缀名
	 * @return String
	 */
	public String createFileName(String patIdcard,String doctUsername,String fileType,String typecode,String ext) {
		return createFileNameWidthDate(patIdcard, doctUsername, fileType, typecode, ext, new Date());
	}
	/**
	 * 向文件追加内容，如果不存在就创建
	 *方法的说明
	 * @param 
	 * @return File
	 */
	public File appendToFile(String path,String fileName,InputStream is) throws ApiException{
		File f = null;
		try{
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			//身份证文件夹 ， 身份证若有同名的文件,把以前的更改名字叫backxxx.xxx
			f = new File(path,fileName);
			if(path.endsWith("idcard_pic")){
				
				if(f.exists()){
					f.renameTo(new File(path,fileName+"_back"));
				}
			}
			
			
		    OutputStream os = new FileOutputStream(f,true);  
            byte[] b = new byte[1024];  
            int bytesRead = 0;  
            while((bytesRead = is.read(b)) != -1){  
                os.write(b, 0,bytesRead);  
            }  
            os.flush();  
            os.close();  
            is.close(); 
		}catch(Exception e){
			e.printStackTrace();
			throw new ApiException("文件创建失败！");
		}
		return f;
	}
	
	public File createFile(String path,String fileName,InputStream is) throws ApiException{
		File f = null;
		try{
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			f = new File(path,fileName);
		    OutputStream os = new FileOutputStream(f);  
            byte[] b = new byte[1024];  
            int bytesRead = 0;  
            while((bytesRead = is.read(b)) != -1){  
                os.write(b, 0,bytesRead);  
            }  
            os.flush();  
            os.close();  
            is.close(); 
		}catch(Exception e){
			e.printStackTrace();
			throw new ApiException("文件创建失败！");
		}
		return f;
	}
   

	public String getPathUpload(String folderName) {
		String filePath = null ;
		try {
			//生成路径
			//获取tomcat 的webapps地址
			filePath= app.uploadPath +File.separator+"upload"+File.separator+folderName;
			File f = new File(filePath);
			if(!f.exists()){
				f.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
	
	public static String getDowloadUrl(String absolutePath){
		if(Validator.isEmpty(absolutePath)){
			return absolutePath;
		}
		ServletContext ctx =  ContextHolderUtils.getRequest().getServletContext();
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ctx.getContextPath();
		
		String[] args = absolutePath.split("webapps");
        String name = args[args.length - 1];
        name = name.replaceAll("\\\\","/");//替换反斜杠，windows下有效
		String url = basePath +"/.."+name;
		return url;
	}

	/**
	 * BEAN TO MAP
	 * <p>Title: beanToMap</p>
	 * <p>Description: TODO</p>
	 * @param clz
	 * @return
	 * @author shunli Yang  2015年9月23日 下午10:49:14
	 * @throws Exception 
	 */
	public static Map<String, String> beanToMap(Object obj) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass()); // 获取类属性 
		// 给 JavaBean 对象的属性赋值 
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			data.put(propertyName, String.valueOf(descriptor.getReadMethod().invoke(obj)==null?"":descriptor.getReadMethod().invoke(obj)));
		}
		return data;
	}

	public Map<String, String> data() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("borrower", "胡桃同学");
		return data;
	}

	
	/**
	 * 非spring 上传
	 * 处理百度上传组件上传上来的文件分片并合并为一个文件
	 * @param 
	 * @return void
	 */
	public  void createFileForBaiduWeb(List<FileItem> fileItems)
			throws UnsupportedEncodingException, IOException,
			FileNotFoundException {
		String id = "";
		String fileName = "";
		//文件存储路径
		String path = getPathUpload("file_data");
		// 如果大于1说明是分片处理
		int chunks = 1;
		int chunk = 0;
		FileItem tempFileItem = null;
		
		for (FileItem fileItem : fileItems) {
			if (fileItem.getFieldName().equals("id")) {
				id = fileItem.getString();
			} else if (fileItem.getFieldName().equals("name")) {
				fileName = new String(fileItem.getString().getBytes("ISO-8859-1"), "UTF-8");
			} else if (fileItem.getFieldName().equals("chunks")) {
				chunks = NumberUtils.toInt(fileItem.getString());
			} else if (fileItem.getFieldName().equals("chunk")) {
				chunk = NumberUtils.toInt(fileItem.getString());
			} else if (fileItem.getFieldName().equals("file")) {
				tempFileItem = fileItem;
			}
		}

		// 临时目录用来存放所有分片文件
		String tempPath = getPathUpload("temp");
		String tempFilePath = tempPath + File.separator + id;
		File tempFileDir = new File(tempFilePath);
		if (!tempFileDir.exists()) {
			tempFileDir.mkdirs();
		}
		// 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台(默认每片为5M)
		String tempFileName = fileName + "_"+ chunk + ".part";
		appendToFile(tempFilePath,tempFileName,tempFileItem.getInputStream());
		// 是否全部上传完成
		// 所有分片都存在才说明整个文件上传完成
		boolean uploadDone = true;
		for (int i = 0; i < chunks; i++) {
			File partFile = new File(tempFileDir, fileName + "_" + i+ ".part");
			if (!partFile.exists()) {
				uploadDone = false;
			}
		}
		// 所有分片文件都上传完成
		// 将所有分片文件合并到一个文件中
		File targetFile = null ;
		if (uploadDone) {
			for (int i = 0; i < chunks; i++) {
				File partFile = new File(tempFileDir, fileName + "_"+ i + ".part");
				FileInputStream partIn = new FileInputStream(partFile); 
				//追加到一个文件,表单提交后还要移动到file_data文件并改名
				targetFile = appendToFile(path,fileName,partIn);//
				partFile.delete();
			}//end for
			tempFileDir.delete();
			
		} else {//
			// 临时文件创建失败,经常发生，把创建失败的文件删除
			if (chunk == chunks - 1) {
				FileUtils.deleteDirectory(tempFileDir);
			}
		}
	}

}
