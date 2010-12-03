package com.ztesoft.crm.business.common.sem.kernel;

import java.io.Serializable;

public class ClassValue implements Serializable {

	
	
	private Object refKey;//引用的class关键字
	
	private String clazz;//class路径字符串

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