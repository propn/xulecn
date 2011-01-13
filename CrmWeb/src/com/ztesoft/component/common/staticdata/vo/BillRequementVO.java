package com.ztesoft.component.common.staticdata.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class BillRequementVO extends ValueObject implements VO {

	private String requireId = "";
	private String billFormatId = "";
	private String billPostCycle = "";
	private String billPostMethod = "";
	private String addrId = "";

	public BillRequementVO() {}

	public BillRequementVO( String prequireId, String pbillFormatId, String pbillPostCycle, String pbillPostMethod, String paddrId ) {
		requireId = prequireId;
		billFormatId = pbillFormatId;
		billPostCycle = pbillPostCycle;
		billPostMethod = pbillPostMethod;
		addrId = paddrId;
	}

	public String getRequireId() {
		return requireId;
	}

	public String getBillFormatId() {
		return billFormatId;
	}

	public String getBillPostCycle() {
		return billPostCycle;
	}

	public String getBillPostMethod() {
		return billPostMethod;
	}

	public String getAddrId() {
		return addrId;
	}

	public void setRequireId(String pRequireId) {
		requireId = pRequireId;
	}

	public void setBillFormatId(String pBillFormatId) {
		billFormatId = pBillFormatId;
	}

	public void setBillPostCycle(String pBillPostCycle) {
		billPostCycle = pBillPostCycle;
	}

	public void setBillPostMethod(String pBillPostMethod) {
		billPostMethod = pBillPostMethod;
	}

	public void setAddrId(String pAddrId) {
		addrId = pAddrId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("REQUIRE_ID",this.requireId);
		hashMap.put("BILL_FORMAT_ID",this.billFormatId);
		hashMap.put("BILL_POST_CYCLE",this.billPostCycle);
		hashMap.put("BILL_POST_METHOD",this.billPostMethod);
		hashMap.put("ADDRESS_ID",this.addrId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.requireId = (String) hashMap.get("REQUIRE_ID");
			this.billFormatId = (String) hashMap.get("BILL_FORMAT_ID");
			this.billPostCycle = (String) hashMap.get("BILL_POST_CYCLE");
			this.billPostMethod = (String) hashMap.get("BILL_POST_METHOD");
			this.addrId = (String) hashMap.get("ADDRESS_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("REQUIRE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "BILL_REQUEMENT";
	}

}
