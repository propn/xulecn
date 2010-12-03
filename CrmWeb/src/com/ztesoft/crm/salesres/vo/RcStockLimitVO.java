package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcStockLimitVO extends ValueObject implements VO {

	private String storageId = "";
	private String familyId = "";
	private String upLimit = "";
	private String downLimit = "";

        private String storageName = "";
        private String familyName = "";

	public RcStockLimitVO() {}

	public RcStockLimitVO( String pstorageId, String pfamilyId, String pupLimit, String pdownLimit ) {
		storageId = pstorageId;
		familyId = pfamilyId;
		upLimit = pupLimit;
		downLimit = pdownLimit;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getFamilyId() {
		return familyId;
	}

	public String getUpLimit() {
		return upLimit;
	}

	public String getDownLimit() {
		return downLimit;
	}

  public String getFamilyName() {
    return familyName;
  }

  public String getStorageName() {
    return storageName;
  }

  public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setFamilyId(String pFamilyId) {
		familyId = pFamilyId;
	}

	public void setUpLimit(String pUpLimit) {
		upLimit = pUpLimit;
	}

	public void setDownLimit(String pDownLimit) {
		downLimit = pDownLimit;
	}

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public void setStorageName(String storageName) {
    this.storageName = storageName;
  }

  public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STORAGE_ID",this.storageId);
		hashMap.put("FAMILY_ID",this.familyId);
		hashMap.put("UP_LIMIT",this.upLimit);
		hashMap.put("DOWN_LIMIT",this.downLimit);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.familyId = (String) hashMap.get("FAMILY_ID");
			this.upLimit = (String) hashMap.get("UP_LIMIT");
			this.downLimit = (String) hashMap.get("DOWN_LIMIT");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("FAMILY_ID");
		arrayList.add("STORAGE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_STOCK_LIMIT";
	}

}
