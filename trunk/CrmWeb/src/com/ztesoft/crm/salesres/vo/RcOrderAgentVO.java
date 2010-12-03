package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcOrderAgentVO extends ValueObject implements VO {

	private String orderId = "";
	private String appId = "";
	private String departId = "";
	private String departName = "";
	private String acceptId = "";
	private String acceptType = "";
        private String rescPrice = "";
        private String discount = "";
        private String realPrice = "";

        public static final String AcceptType_In = "i";
        public static final String AcceptType_Out = "o";

	public RcOrderAgentVO() {}

	public RcOrderAgentVO( String porderId, String pappId, String pdepartId, String pdepartName, String pacceptId, String pacceptType, String prescPrice, String pdiscount, String prealPrice ) {
		orderId = porderId;
		appId = pappId;
		departId = pdepartId;
		departName = pdepartName;
		acceptId = pacceptId;
		acceptType = pacceptType;
                rescPrice = prescPrice;
                discount = pdiscount;
                realPrice = prealPrice;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getAppId() {
		return appId;
	}

	public String getDepartId() {
		return departId;
	}

	public String getDepartName() {
		return departName;
	}

	public String getAcceptId() {
		return acceptId;
	}

	public String getAcceptType() {
		return acceptType;
	}

  public String getRealPrice() {
    return realPrice;
  }

  public String getRescPrice() {
    return rescPrice;
  }

  public String getDiscount() {
    return discount;
  }

  public void setOrderId(String pOrderId) {
		orderId = pOrderId;
	}

	public void setAppId(String pAppId) {
		appId = pAppId;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setDepartName(String pDepartName) {
		departName = pDepartName;
	}

	public void setAcceptId(String pAcceptId) {
		acceptId = pAcceptId;
	}

	public void setAcceptType(String pAcceptType) {
		acceptType = pAcceptType;
	}

  public void setRealPrice(String realPrice) {
    this.realPrice = realPrice;
  }

  public void setRescPrice(String rescPrice) {
    this.rescPrice = rescPrice;
  }

  public void setDiscount(String discount) {
    this.discount = discount;
  }

  public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORDER_ID",this.orderId);
		hashMap.put("APP_ID",this.appId);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("DEPART_NAME",this.departName);
		hashMap.put("ACCEPT_ID",this.acceptId);
		hashMap.put("ACCEPT_TYPE",this.acceptType);
                hashMap.put("RESOURCE_PRICE",this.rescPrice);
                hashMap.put("DISCOUNT",this.discount);
                hashMap.put("REAL_PRICE",this.realPrice);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orderId = (String) hashMap.get("ORDER_ID");
			this.appId = (String) hashMap.get("APP_ID");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.departName = (String) hashMap.get("DEPART_NAME");
			this.acceptId = (String) hashMap.get("ACCEPT_ID");
			this.acceptType = (String) hashMap.get("ACCEPT_TYPE");
                        this.rescPrice = (String) hashMap.get("RESOURCE_PRICE");
                        this.discount = (String) hashMap.get("DISCOUNT");
                        this.realPrice = (String) hashMap.get("REAL_PRICE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("APP_ID");
		arrayList.add("ORDER_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_ORDER_AGENT";
	}

}
