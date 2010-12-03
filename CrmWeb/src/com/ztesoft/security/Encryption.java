/**
 * 
 */
package com.ztesoft.security;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.PBEParameterSpec;

/**
 * ������
 * 
 * @author tmz
 * 
 */
public class Encryption
{
	/**
	 * ����
	 * 
	 * @param data
	 *            ����
	 * @param password
	 *            ����
	 * @param salt
	 *            ��
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String date) throws Exception
	{
		byte[] input = date.getBytes();

		Key key = KeyMes.toKey(KeyMes.password);

		PBEParameterSpec paramSpec = new PBEParameterSpec(KeyMes.initSalt(),
				100);
		Cipher cipher = Cipher.getInstance(KeyMes.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

		String outStr = KeyMes.encryptBASE64(cipher.doFinal(input));

		return outStr;

	}

}

