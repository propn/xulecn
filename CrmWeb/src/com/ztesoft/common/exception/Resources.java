package com.ztesoft.common.exception;

import java.io.IOException;
import java.util.PropertyResourceBundle;

/**
 * 
 * <p>Description: ��Դ��λ���ļ�</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-26
 * @Version       : 1.0
 */
public class Resources extends PropertyResourceBundle {
	
	/**
	 * ���캯��,���ı��ļ��е����ı�����
	 * @throws IOException
	 */
	public Resources() throws java.io.IOException {
		super(Resources.class.getResourceAsStream("Resources.properties"));
	}

}