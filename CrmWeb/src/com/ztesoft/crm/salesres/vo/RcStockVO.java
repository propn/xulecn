package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcStockVO extends ValueObject implements VO {

	private String storageId = "";
	private String lanId = "";
	private String resourceLevel = "";
	private String storageName = "";

	private String salesRescId = "";

	private String salesRescName = "";

	private String stockAmount = "";

        private String rescState = "";

        /** 库存上下限信息 **/
        private String upLimit = "";
	private String downLimit = "";
	private String downStorageId = "";//下级仓库ID，以逗号分割；
	private String downStorageName = "";//下级仓库Name，以逗号分割；
	private String departId = "";//
	private String operId = "";
	
	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public RcStockVO() {}

	public RcStockVO( String pstorageId, String psalesRescId, String pstockAmount ) {
		storageId = pstorageId;
		salesRescId = psalesRescId;
		stockAmount = pstockAmount;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getStockAmount() {
		return stockAmount;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setStockAmount(String pStockAmount) {
		stockAmount = pStockAmount;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STORAGE_ID",this.storageId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("STOCK_AMOUNT",this.stockAmount);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.stockAmount = (String) hashMap.get("STOCK_AMOUNT");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("SALES_RESOURCE_ID");
		arrayList.add("STORAGE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_STOCK";
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

  public String getDownLimit() {
    return downLimit;
  }

  public String getUpLimit() {
    return upLimit;
  }

  public String getRescState() {
    return rescState;
  }

  public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

  public void setDownLimit(String downLimit) {
    this.downLimit = downLimit;
  }

  public void setUpLimit(String upLimit) {
    this.upLimit = upLimit;
  }

  public void setRescState(String rescState) {
    this.rescState = rescState;
  }

public String getDownStorageId() {
	return downStorageId;
}

public void setDownStorageId(String downStorageId) {
	this.downStorageId = downStorageId;
}

public String getDownStorageName() {
	return downStorageName;
}

public void setDownStorageName(String downStorageName) {
	this.downStorageName = downStorageName;
}

public String getLanId() {
	return lanId;
}

public void setLanId(String lanId) {
	this.lanId = lanId;
}

public String getResourceLevel() {
	return resourceLevel;
}

public void setResourceLevel(String resourceLevel) {
	this.resourceLevel = resourceLevel;
}

}
