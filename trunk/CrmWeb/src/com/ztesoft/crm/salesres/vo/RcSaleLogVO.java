package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcSaleLogVO extends ValueObject implements VO {
	private String manufacturer = "";
	private String provider = "";
	private String shopkeeper = "";
	private String currState = "";
	
	private String logId = "";
	private String rescInstanceId = "";
	private String salesRescId = "";
	private String lanId = "";
	private String lanName = "";
	private String saleTime = "";
	private String rescInstance2 = "";
	private String departId = "";
	private String departName = "";
	private String operId = "";
	private String price = "";
	private String style = "";
	private String isOut = "";
	private String beforeState = "";
	private String afterState = "";
	private String storageId = "";
	private String stockAmount = "";
	private String prodId = "";
	private String prodNo = "";
	private String custId = "";
    private String imei = "";
    private String custName = "";
    private String dealType = "";
	private String dealInfo = "";
	
	private String produceNo = "";
    /** 前台营业受理编号 **/
    private String acceptId = "";
    /** 业务类型，销售业务:SAL **/
    private String biztype = "";

	private String orgName= "";
	private String storageName = "";
	private String salesRescName ="";
	
    /** 辅助字段，生成时间 **/
	private String createDate = ""; 
	private String state = "";
	private String rescState = "";
	private String manageMode = "";
	private String operName = "";
	private String familyId = "";
	private String attriValue = "";// 界面使用，没有作用


	public String getAttriValue() {
		return attriValue;
	}

	public void setAttriValue(String attriValue) {
		this.attriValue = attriValue;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getManageMode() {
		return manageMode;
	}

	public void setManageMode(String manageMode) {
		this.manageMode = manageMode;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getRescState() {
		return rescState;
	}

	public void setRescState(String rescState) {
		this.rescState = rescState;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public RcSaleLogVO() {}

	public RcSaleLogVO( String plogId, String prescInstanceId, String psalesRescId, String planId, String psaleTime, String prescInstance2, String pdepartId, String poperId, String pprice, String pstyle, String pisOut, String pbeforeState, String pafterState, String pstorageId, String pstockAmount, String pprodNo, String pcustId ) {
		logId = plogId;
		rescInstanceId = prescInstanceId;
		salesRescId = psalesRescId;
		lanId = planId;
		saleTime = psaleTime;
		rescInstance2 = prescInstance2;
		departId = pdepartId;
		operId = poperId;
		price = pprice;
		style = pstyle;
		isOut = pisOut;
		beforeState = pbeforeState;
		afterState = pafterState;
		storageId = pstorageId;
		stockAmount = pstockAmount;
		prodNo = pprodNo;
		custId = pcustId;
	}

	public String getLogId() {
		return logId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getLanId() {
		return lanId;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public String getRescInstance2() {
		return rescInstance2;
	}

	public String getDepartId() {
		return departId;
	}

	public String getOperId() {
		return operId;
	}

	public String getPrice() {
		return price;
	}

	public String getStyle() {
		return style;
	}

	public String getBeforeState() {
		return beforeState;
	}

	public String getAfterState() {
		return afterState;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getStockAmount() {
		return stockAmount;
	}

	public String getProdNo() {
		return prodNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setLogId(String pLogId) {
		logId = pLogId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setSaleTime(String pSaleTime) {
		saleTime = pSaleTime;
	}

	public void setRescInstance2(String pRescInstance2) {
		rescInstance2 = pRescInstance2;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setPrice(String pPrice) {
		price = pPrice;
	}

	public void setStyle(String pStyle) {
		style = pStyle;
	}

	public void setBeforeState(String pBeforeState) {
		beforeState = pBeforeState;
	}

	public void setAfterState(String pAfterState) {
		afterState = pAfterState;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setStockAmount(String pStockAmount) {
		stockAmount = pStockAmount;
	}

	public void setProdNo(String pProdNo) {
		prodNo = pProdNo;
	}

	public void setCustId(String pCustId) {
		custId = pCustId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LOG_ID",this.logId);
		hashMap.put("RESOURCE_INSTANCE_ID",this.rescInstanceId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("SALE_TIME",this.saleTime);
		hashMap.put("RESOURCE_INSTANCE2",this.rescInstance2);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("PRICE",this.price);
		hashMap.put("STYLE",this.style);
		hashMap.put("IS_OUT",this.isOut);
		hashMap.put("BEFORE_STATE",this.beforeState);
		hashMap.put("AFTER_STATE",this.afterState);
		hashMap.put("STORAGE_ID",this.storageId);
		hashMap.put("STOCK_AMOUNT",this.stockAmount);
		hashMap.put("PRODUCT_NO",this.prodNo);
		hashMap.put("CUST_ID",this.custId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.logId = (String) hashMap.get("LOG_ID");
			this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.saleTime = (String) hashMap.get("SALE_TIME");
			this.rescInstance2 = (String) hashMap.get("RESOURCE_INSTANCE2");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.price = (String) hashMap.get("PRICE");
			this.style = (String) hashMap.get("STYLE");
			this.isOut = (String) hashMap.get("IS_OUT");
			this.beforeState = (String) hashMap.get("BEFORE_STATE");
			this.afterState = (String) hashMap.get("AFTER_STATE");
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.stockAmount = (String) hashMap.get("STOCK_AMOUNT");
			this.prodNo = (String) hashMap.get("PRODUCT_NO");
			this.custId = (String) hashMap.get("CUST_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("LOG_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_SALE_LOG";
	}

	public String getRescInstanceId() {
		return rescInstanceId;
	}

	public void setRescInstanceId(String rescInstanceId) {
		this.rescInstanceId = rescInstanceId;
	}

	public String getIsOut() {
		return isOut;
	}

	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSalesRescName() {
		return salesRescName;
	}

	public void setSalesRescName(String salesRescName) {
		this.salesRescName = salesRescName;
	}

	public String getStorageName() {
		return storageName;
	}

  public String getDealType() {
    return dealType;
  }

  public String getDealInfo() {
    return dealInfo;
  }

  public String getImei() {
    return imei;
  }

  public String getAcceptId() {
    return acceptId;
  }

  public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

  public void setDealType(String dealType) {
    this.dealType = dealType;
  }

  public void setDealInfo(String dealInfo) {
    this.dealInfo = dealInfo;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public void setAcceptId(String acceptId) {
    this.acceptId = acceptId;
  }

public String getProduceNo() {
	return produceNo;
}

public void setProduceNo(String produceNo) {
	this.produceNo = produceNo;
}

public String getCustName() {
	return custName;
}

public void setCustName(String custName) {
	this.custName = custName;
}

public String getProdId() {
	return prodId;
}

public void setProdId(String prodId) {
	this.prodId = prodId;
}


public String getCurrState() {
	return currState;
}
public String getBiztype() {
	return biztype;

}

public void setCurrState(String currState) {
	this.currState = currState;
}

public String getDepartName() {
	return departName;
}

public void setDepartName(String departName) {
	this.departName = departName;
}

public String getLanName() {
	return lanName;
}

public void setLanName(String lanName) {
	this.lanName = lanName;
}

public String getManufacturer() {
	return manufacturer;
}

public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
}

public String getProvider() {
	return provider;
}

public void setProvider(String provider) {
	this.provider = provider;
}

public String getShopkeeper() {
	return shopkeeper;
}

public void setShopkeeper(String shopkeeper) {
	this.shopkeeper = shopkeeper;
}


public void setBiztype(String biztype) {
	this.biztype = biztype;
}
}

