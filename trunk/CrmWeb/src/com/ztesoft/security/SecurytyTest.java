/**
 * 
 */
package com.ztesoft.security;

/**
 * ���ܡ�����ʾ����
 * @author tmz
 *
 */
public class SecurytyTest {
	public static void main(String[] args)
	{
		try
		{
			String inputStr = "��ã����ܣ�����";  
	        System.out.println("ԭ��: " + inputStr);	   
	        
	        //����
	        String x = Encryption.encrypt(inputStr);	  
	        System.out.println("���ܺ�: " + x); 
	        
	        //����
	        String outputStr = Decryption.decrypt(x); 	  
	        System.out.println("���ܺ�: " + outputStr);  
			
			
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
