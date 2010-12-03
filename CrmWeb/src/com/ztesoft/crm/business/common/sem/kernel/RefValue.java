package com.ztesoft.crm.business.common.sem.kernel;

import java.io.Serializable;

public class RefValue implements Serializable {

	private Object refKey;
	
	public RefValue(Object refKey){
		this.refKey=refKey;

	}
	public String toString() {
		return "RefKey:" + refKey;
	}
	public Object getRefKey() {
		return refKey;
	}
	public void setRefKey(Object refKey) {
		this.refKey = refKey;
	}



}