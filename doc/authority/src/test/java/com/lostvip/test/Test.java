package com.lostvip.test;

import com.lostad.app.security.util.MD5Utils;
import com.lostad.app.security.util.SignUtil;

public class Test {

	public static void main(String[] args) {
		String str = "111111";
		String md5 = MD5Utils.md5(str);
		System.out.println(md5);
		String md5_hex = MD5Utils.md5Hex(str);
		System.out.println(md5_hex);
	}

}
