package com.lostvip.test.upload;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import com.lostad.app.security.util.SignUtil;


/**
 * java通过模拟post方式提交表单实现图片上传功能实例
 * 其他文件类型可以传入 contentType 实现
 * @author zdz8207
 * {@link http://www.cnblogs.com/zdz8207/}
 * @version 1.0
 */
public class TestFileFormUpload {

    public static void main(String[] args) {
    	String url = "http://localhost:9002/his/apiOpen/fileUploadApi/data";
        String path = "G:\\Ecg.zip";
        Map<String, String> textMap = new HashMap<String, String>();//表单域
        Map<String, String> fileMap = new HashMap<String, String>();//文件域
        Map<String, String> headerMap = new HashMap<String, String>();//header头数据
        String params = "doctorId=520401199112263448patientId=520401199112263448typeCode=lb";
        String sign = SignUtil.signMD5(params,"1234");
        //header
        headerMap.put("appId","hly_app");
        headerMap.put("signType","MD5");
        headerMap.put("sign", sign);
        //form input 可以设置多个input的name，value
        textMap.put("patientId", "520401199112263448");
        textMap.put("doctorId", "520401199112263448");
        textMap.put("typeCode", "lb");
        //file 设置file的name，路径
        fileMap.put("file1", path);//文件不参与生成签名
        //String ret = formUpload(url,textMap,headerMap,"file1",path);
        String ret = formUploadMult(url, textMap, fileMap, headerMap);
        //(String urlStr, Map<String, String> params,Map<String, String> fileMap,Map<String, String> headerMap)
        System.out.println(ret);
    }
    
   
    /**
     * 多上传文件
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @param contentType 没有传入文件类型默认采用application/octet-stream
     * contentType非空采用filename匹配默认的图片类型
     * @return 返回response数据
     */
    public static String formUploadMult(String urlStr, Map<String, String> params,Map<String, String> fileMap,Map<String, String> headerMap) {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------3s3f35df7569gv89";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            //设置必要的header信息
            //============header===========================================
            if (headerMap != null) {//header对象
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = headerMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String name = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    conn.setRequestProperty(name,value);
                }
            }
            OutputStream out = new DataOutputStream(conn.getOutputStream());
          //============input===========================================
            if (params != null) {//表单内容
            	 StringBuilder sb = new StringBuilder();
     	        if(params!=null){
     	        	 for (Map.Entry<String, String> entry : params.entrySet()) {//构建表单字段内容 
     	 	            sb.append("--");
     	 	            sb.append(BOUNDARY);
     	 	            sb.append("\r\n");
     	 	            sb.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
     	 	            sb.append(entry.getValue());
     	 	            sb.append("\r\n");
     	 	        }
     	        }
     	        System.out.println(sb.toString());
                out.write(sb.toString().getBytes());
            }
            
            
          //============file===========================================
            if (fileMap != null) {//多个文件
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + file.getName() + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            System.out.println("\r\n--" + BOUNDARY + "--\r\n");
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }
}