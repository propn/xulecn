package com.ztesoft.crm.business.common.sem.kernel;

import java.io.Serializable;

public class ClassValue implements Serializable {

	
	
	private Object refKey;//���õ�class�ؼ���
	
	private String clazz;//class·���ַ���

	public ClassValue(Object refKey,String clazz) {
		this.clazz = clazz;
		this.refKey=refKey;
	}

	public String toString() {
		return "Class:" + clazz;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Object getRefKey() {
		return refKey;
	}

	public void setRefKey(Object refKey) {
		this.refKey = refKey;
	}
}