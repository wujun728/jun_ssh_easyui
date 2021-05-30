package com.lostad.app.security.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

public class MD5Utils {
	/**
	 * 这个方法可用作负载均衡算法（比如通过ip分配tomcat）
	 * @param input
	 * @return
	 */
	public static int md5Str2Int(String input) {
		byte[] code = null;
        try {
            code = MessageDigest.getInstance("md5").digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            code = input.getBytes();
        }
        BigInteger bi = new BigInteger(code);
        return bi.intValue();
	}
	/**
	 * 对字符串进行Md5加密
	 * 
	 * @param input 原文
	 * @return md5后的密文
	 */
	@Deprecated
	public static String md5(String input) {
		byte[] code = null;
        try {
            code = MessageDigest.getInstance("md5").digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            code = input.getBytes();
        }
        BigInteger bi = new BigInteger(code);
        return bi.abs().toString(32).toUpperCase();
	}
	
	public static String md5Hex(String ds) {
		String dist = null ;
		try {
			dist = DigestUtils.md5Hex(ds.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return dist;
	}
	/**
	 * 对字符串进行Md5加密
	 * 
	 * @param input 原文
	 * @param salt 随机数
	 * @return string
	 */
	public static String generatePasswordMD5(String input, String salt) {
		if(StringUtils.isEmpty(salt)) {
			salt = "";
		}
		return md5Hex(salt + md5Hex(input));
	}
	
	public static void main(String[] args) {
		System.out.println(md5Hex("111111"));
		System.out.println(md5Str2Int("111111"));
		System.out.println(md5Str2Int("111111"));
		System.out.println(md5Str2Int("111111"));
		System.out.println(md5Str2Int("111111"));
	}
	
}
