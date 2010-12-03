package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RescNumVO extends ValueObject implements VO {

	private String salesRescId = "";
	private String storageId = "";
	private String familyId = "";
	private String num = "";
	private String lanId = "";
	private String pkCalbody = "";
	private String cinventoryid = "";
	private String vbatchcode = "";

	public RescNumVO() {}

	public RescNumVO( String psalesRescId, String pstorageId, String pfamilyId, String pnum, String planId, String ppkCalbody, String pcinventoryid, String pvbatchcode ) {
		salesRescId = psalesRescId;
		storageId = pstorageId;
		familyId = pfamilyId;
		num = pnum;
		lanId = planId;
		pkCalbody = ppkCalbody;
		cinventoryid = pcinventoryid;
		vbatchcode = pvbatchcode;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getFamilyId() {
		return familyId;
	}

	public String getNum() {
		return num;
	}

	public String getLanId() {
		return lanId;
	}

	public String getPkCalbody() {
		return pkCalbody;
	}

	public String getCinventoryid() {
		return cinventoryid;
	}

	public String getVbatchcode() {
		return vbatchcode;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setFamilyId(String pFamilyId) {
		familyId = pFamilyId;
	}

	public void setNum(String pNum) {
		num = pNum;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setPkCalbody(String pPkCalbody) {
		pkCalbody = pPkCalbody;
	}

	public void setCinventoryid(String pCinventoryid) {
		cinventoryid = pCinventoryid;
	}

	public void setVbatchcode(String pVbatchcode) {
		vbatchcode = pVbatchcode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("STORAGE_ID",this.storageId);
		hashMap.put("FAMILY_ID",this.familyId);
		hashMap.put("NUM",this.num);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("PK_CALBODY",this.pkCalbody);
		hashMap.put("CINVENTORYID",this.cinventoryid);
		hashMap.put("VBATCHCODE",this.vbatchcode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.familyId = (String) hashMap.get("FAMILY_ID");
			this.num = (String) hashMap.get("NUM");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.pkCalbody = (String) hashMap.get("PK_CALBODY");
			this.cinventoryid = (String) hashMap.get("CINVENTORYID");
			this.vbatchcode = (String) hashMap.get("VBATCHCODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CINVENTORYID");
		arrayList.add("LAN_ID");
		arrayList.add("PK_CALBODY");
		arrayList.add("SALES_RESOURCE_ID");
		arrayList.add("STORAGE_ID");
		arrayList.add("VBATCHCODE");
		return arrayList;
	}

	public String getTableName() {
		return "RESOURCE_NUM";
	}

}
