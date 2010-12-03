package com.ztesoft.crm.salesres.common;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
/**
 * 
 * @author wu.xueming
 *
 */
public class EncryptDESUtils {
	private static EncryptDESUtils encryptDESUtils = null;
	private static Cipher encryptCipher=null;
	private static Cipher decryptCipher=null;
	private static Key key = null;
	
	static{
		
			try {
				File file = new File("myKey.dat");
				if(file.exists()==false){
					file.createNewFile();
					KeyGenerator keygen = KeyGenerator.getInstance("DES");
					key = (Key) keygen.generateKey();
					java.io.ObjectOutputStream outs = new java.io.ObjectOutputStream(new java.io.FileOutputStream(file));
					outs.writeObject(key);
					outs.flush();
					outs.close();
				}else{
					java.io.ObjectInputStream ins = new java.io.ObjectInputStream(new java.io.FileInputStream(file));
					 key = (Key)ins.readObject();
					 ins.close();
					 if(key==null){
						 key = (Key) KeyGenerator.getInstance("DES").generateKey();
						 java.io.ObjectOutputStream outs = new java.io.ObjectOutputStream(new java.io.FileOutputStream(file));
						outs.writeObject(key);
						outs.flush();
						outs.close();
					 }
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				
			}
		
	}
	private EncryptDESUtils() {
		
	}
	//加密方法
	public String encrypt(String string){
		try {
			if (string == null ||string.equals(""))return "";
			if(encryptCipher == null){
				encryptCipher = Cipher.getInstance("DES");
			}
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] bs = encryptCipher.doFinal((string).getBytes());
			return this.bytes2String(bs);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{}
		return "";
	}
	//解密方法
	public String decrypt(String string){
		try {
			if (string == null ||string.equals(""))return "";
			if(decryptCipher == null){
				decryptCipher = Cipher.getInstance("DES");
			}
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
			byte[] bs = decryptCipher.doFinal(this.string2Bytes(string));
			return new String(bs);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{}
		return "";
	}
	
	public static EncryptDESUtils getInstance(){
		if(encryptDESUtils==null){
			encryptDESUtils =  new EncryptDESUtils();
		}
		return encryptDESUtils;
	}
	
	private String bytes2String(byte[] bs){
		 int iLen = bs.length;
		  StringBuffer sb = new StringBuffer(iLen * 2);
		  for (int i = 0; i < iLen; i++) {
		   int intTmp = bs[i];
		   while (intTmp < 0) {
		    intTmp = intTmp + 256;
		   }
		   if (intTmp < 16) {
		    sb.append("0");
		   }
		   System.out.println(intTmp+","+Integer.toString(intTmp, 16));
		   sb.append(Integer.toString(intTmp, 16));
		  }
		  return sb.toString();
	}
	
	private byte[] string2Bytes(String str){
		 byte[] arrB = str.getBytes();
		  int iLen = arrB.length;
		  byte[] arrOut = new byte[iLen / 2];
		  for (int i = 0; i < iLen; i = i + 2) {
		   String strTmp = new String(arrB, i, 2);
		   System.out.println(strTmp+","+(byte) Integer.parseInt(strTmp, 16));
		   arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		  }
		  return arrOut;
	}
	
	public static void main(String[] args) {
//		String s = "ff";
//		byte i = (byte) Integer.parseInt(s,16);
//		System.out.println(i);
//		String str = EncryptDESUtils.getInstance().encrypt("wu.xueming");
//		System.out.println(str);
//		String str2 = EncryptDESUtils.getInstance().decrypt(str);// 32dcda80913c6ccc
//		System.out.println(str2);
		byte[] b = new String("wu.xueming").getBytes();
		for(int i=0;i<b.length;i++)
			System.out.println(b[i]);
	}

}
