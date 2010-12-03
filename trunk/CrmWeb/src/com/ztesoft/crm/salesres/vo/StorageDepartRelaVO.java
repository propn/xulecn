package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class StorageDepartRelaVO extends ValueObject implements VO {

	private String storageId = "";
	private String departId = "";
	private String departName = "";

	public StorageDepartRelaVO() {}

	public StorageDepartRelaVO( String pstorageId, String pdepartId , String pdepartName) {
		storageId = pstorageId;
		departId = pdepartId;
		departName = pdepartName;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STORAGE_ID",this.storageId);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("DEPART_NAME",this.departName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.departName = (String) hashMap.get("DEPART_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "STORAGE_DEPART_RELA";
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

}
