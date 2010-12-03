package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class SalesRescIdRelaVO extends ValueObject implements VO {

	private String ncSalesRescId = "";
	private String dcDeviceScode = "";
	private String salesRescId = "";

	public SalesRescIdRelaVO() {}

	public SalesRescIdRelaVO( String pncSalesRescId, String pdcDeviceScode, String psalesRescId ) {
		ncSalesRescId = pncSalesRescId;
		dcDeviceScode = pdcDeviceScode;
		salesRescId = psalesRescId;
	}

	public String getNcSalesRescId() {
		return ncSalesRescId;
	}

	public String getDcDeviceScode() {
		return dcDeviceScode;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public void setNcSalesRescId(String pNcSalesRescId) {
		ncSalesRescId = pNcSalesRescId;
	}

	public void setDcDeviceScode(String pDcDeviceScode) {
		dcDeviceScode = pDcDeviceScode;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("NC_SALES_RESOURCE_ID",this.ncSalesRescId);
		hashMap.put("DC_DEVICE_SCODE",this.dcDeviceScode);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.ncSalesRescId = (String) hashMap.get("NC_SALES_RESOURCE_ID");
			this.dcDeviceScode = (String) hashMap.get("DC_DEVICE_SCODE");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("DC_DEVICE_SCODE");
		arrayList.add("NC_SALES_RESOURCE_ID");
		arrayList.add("SALES_RESOURCE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "SALES_RESOURCE_ID_RELA";
	}

}
