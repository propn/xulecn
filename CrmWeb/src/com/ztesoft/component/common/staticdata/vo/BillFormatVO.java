package com.ztesoft.component.common.staticdata.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class BillFormatVO extends ValueObject implements VO {

	private String billFormatId = "";
	private String remarkId = "";
	private String formatName = "";
	private String templateFile = "";

	public BillFormatVO() {}

	public BillFormatVO( String pbillFormatId, String premarkId, String pformatName, String ptemplateFile ) {
		billFormatId = pbillFormatId;
		remarkId = premarkId;
		formatName = pformatName;
		templateFile = ptemplateFile;
	}

	public String getBillFormatId() {
		return billFormatId;
	}

	public String getRemarkId() {
		return remarkId;
	}

	public String getFormatName() {
		return formatName;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setBillFormatId(String pBillFormatId) {
		billFormatId = pBillFormatId;
	}

	public void setRemarkId(String pRemarkId) {
		remarkId = pRemarkId;
	}

	public void setFormatName(String pFormatName) {
		formatName = pFormatName;
	}

	public void setTemplateFile(String pTemplateFile) {
		templateFile = pTemplateFile;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("BILL_FORMAT_ID",this.billFormatId);
		hashMap.put("REMARK_ID",this.remarkId);
		hashMap.put("FORMAT_NAME",this.formatName);
		hashMap.put("TEMPLATE_FILE",this.templateFile);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.billFormatId = (String) hashMap.get("BILL_FORMAT_ID");
			this.remarkId = (String) hashMap.get("REMARK_ID");
			this.formatName = (String) hashMap.get("FORMAT_NAME");
			this.templateFile = (String) hashMap.get("TEMPLATE_FILE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("BILL_FORMAT_ID");
		return arrayList;
	}

	public String getTableName() {
		return "BILL_FORMAT";
	}

}
