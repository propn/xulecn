package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcOrderSegInfoVO extends ValueObject implements VO {

	private String orderId = "";
	private String preCode = "";
	private String postCode = "";
	private String resBCode = "";
	private String resECode = "";
	private String flag = "";

	public RcOrderSegInfoVO() {}

	public List getKeyFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadFromHashMap(HashMap map) {
		// TODO Auto-generated method stub
		
	}

	public HashMap unloadToHashMap() {
		// TODO Auto-generated method stub
		return null;
	}



	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPreCode() {
		return preCode;
	}

	public void setPreCode(String preCode) {
		this.preCode = preCode;
	}

	public String getResBCode() {
		return resBCode;
	}

	public void setResBCode(String resBCode) {
		this.resBCode = resBCode;
	}

	public String getResECode() {
		return resECode;
	}

	public void setResECode(String resECode) {
		this.resECode = resECode;
	}


	
	
}
