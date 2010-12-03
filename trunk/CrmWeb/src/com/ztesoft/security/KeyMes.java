/**
 * 
 */
package com.ztesoft.security;

import java.security.Key;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * ���ܡ����ܹ�����
 * @author tmz
 *
 */

public class KeyMes
{
	public static final String ALGORITHM = "PBEWITHMD5andDES";  
	public static final String password="ztesoft";
	
	  
    /**
	 * �γ�ʼ��
	 * 
	 * @return
	 * @throws Exception
	 */  
    public static byte[] initSalt() throws Exception {  
        byte[] salt = new byte[8];  
        Random random = new Random(8);  
        random.nextBytes(salt);  
        return salt;  
    }  

    /**
	 * ת����Կ<br>
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */  
    public static Key toKey(String password) throws Exception {  
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
        SecretKey secretKey = keyFactory.generateSecret(keySpec);  
  
        return secretKey;  
    }
    
    /** 
     * BASE64���� 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptBASE64(String key) throws Exception {  
        return (new BASE64Decoder()).decodeBuffer(key);  
    }  
  
    /** 
     * BASE64���� 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptBASE64(byte[] key) throws Exception {  
        return (new BASE64Encoder()).encodeBuffer(key);  
    }  
  
}

