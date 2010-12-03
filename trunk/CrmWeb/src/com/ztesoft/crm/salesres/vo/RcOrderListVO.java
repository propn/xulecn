package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcOrderListVO extends ValueObject implements VO {

	private String orderId = "";
	private String rescInstanceId = "";
	private String salesRescId = "";
	private String rescInstanceCode = "";
	private String storageIdFrom = "";
	private String storageIdTo = "";
	private String storageNameFrom = "";
	private String storageNameTo = "";

	public RcOrderListVO() {}

	public RcOrderListVO( String porderId, String prescInstanceId, String psalesRescId, String prescInstanceCode ) {
		orderId = porderId;
		rescInstanceId = prescInstanceId;
		salesRescId = psalesRescId;
		rescInstanceCode = prescInstanceCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getRescInstanceId() {
		return rescInstanceId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getRescInstanceCode() {
		return rescInstanceCode;
	}

	public void setOrderId(String pOrderId) {
		orderId = pOrderId;
	}

	public void setRescInstanceId(String pRescInstanceId) {
		rescInstanceId = pRescInstanceId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setRescInstanceCode(String pRescInstanceCode) {
		rescInstanceCode = pRescInstanceCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORDER_ID",this.orderId);
		hashMap.put("RESOURCE_INSTANCE_ID",this.rescInstanceId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("RESOURCE_INSTANCE_CODE",this.rescInstanceCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orderId = (String) hashMap.get("ORDER_ID");
			this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.rescInstanceCode = (String) hashMap.get("RESOURCE_INSTANCE_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ORDER_ID");
		arrayList.add("SALES_RESOURCE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_ORDER_LIST";
	}
public String toString(){
return "getOrderId "+this.getOrderId()+"getRescInstanceId "+this.getRescInstanceId()+"getSalesRescId "+this.getSalesRescId()+"getRescInstanceCode "+this.getRescInstanceCode();
}

public String getStorageIdFrom() {
	return storageIdFrom;
}

public void setStorageIdFrom(String storageIdFrom) {
	this.storageIdFrom = storageIdFrom;
}

public String getStorageIdTo() {
	return storageIdTo;
}

public void setStorageIdTo(String storageIdTo) {
	this.storageIdTo = storageIdTo;
}

public String getStorageNameFrom() {
	return storageNameFrom;
}

public void setStorageNameFrom(String storageNameFrom) {
	this.storageNameFrom = storageNameFrom;
}

public String getStorageNameTo() {
	return storageNameTo;
}

public void setStorageNameTo(String storageNameTo) {
	this.storageNameTo = storageNameTo;
}
}
