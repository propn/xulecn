package com.ztesoft.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

public final class SecurityPassword {
	private static Logger logger = Logger.getLogger(SecurityPassword.class);

	public static String getDigestPasswd(String password) {
		MessageDigest userNamePassword = null;
		String encode = "";
		try {
			/* ʹ��MD5�����㷨 */
			userNamePassword = MessageDigest.getInstance("MD5");
			userNamePassword.update(password.getBytes());
			byte[] digestPassword = userNamePassword.digest();
			BASE64Encoder base64Encoder = new BASE64Encoder();
			encode = base64Encoder.encode(digestPassword);
		} catch (NoSuchAlgorithmException ex) {
			logger.debug("Get SecurityPassword MessageDigest failed!!!");
		}
		return encode;
	}

}
