package com.ztesoft.oaas.vo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class MmDataPrivRuleVO extends ValueObject implements VO {

	private String privType = "";
	private String getValSql = "";
	private String ifRegionRela = "";
	private String dataTableCode = "";
	private String transSql = "";
	private String getValMode = "";

	public String getGetValMode() {
		return getValMode;
	}

	public void setGetValMode(String getValMode) {
		this.getValMode = getValMode;
	}

	public MmDataPrivRuleVO() {}

	public MmDataPrivRuleVO( String pprivType, String pgetValSql, String pifRegionRela, String pdataTableCode, String ptransSql , String pgetvalmode) {
		privType = pprivType;
		getValSql = pgetValSql;
		ifRegionRela = pifRegionRela;
		dataTableCode = pdataTableCode;
		transSql = ptransSql;
		this.getValMode = pgetvalmode ;
	}

	public String getPrivType() {
		return privType;
	}

	public String getGetValSql() {
		return getValSql;
	}

	public String getIfRegionRela() {
		return ifRegionRela;
	}

	public String getDataTableCode() {
		return dataTableCode;
	}

	public String getTransSql() {
		return transSql;
	}

	public void setPrivType(String pPrivType) {
		privType = pPrivType;
	}

	public void setGetValSql(String pGetValSql) {
		getValSql = pGetValSql;
	}

	public void setIfRegionRela(String pIfRegionRela) {
		ifRegionRela = pIfRegionRela;
	}

	public void setDataTableCode(String pDataTableCode) {
		dataTableCode = pDataTableCode;
	}

	public void setTransSql(String pTransSql) {
		transSql = pTransSql;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRIVILEGE_TYPE",this.privType);
		hashMap.put("GET_VAL_SQL",this.getValSql);
		hashMap.put("IF_REGION_RELA",this.ifRegionRela);
		hashMap.put("DATA_TABLE_CODE",this.dataTableCode);
		hashMap.put("TRANS_SQL",this.transSql);
		hashMap.put("GET_VAL_MODE",this.getValMode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.privType = (String) hashMap.get("PRIVILEGE_TYPE");
			this.getValSql = (String) hashMap.get("GET_VAL_SQL");
			this.ifRegionRela = (String) hashMap.get("IF_REGION_RELA");
			this.dataTableCode = (String) hashMap.get("DATA_TABLE_CODE");
			this.transSql = (String) hashMap.get("TRANS_SQL");
			this.getValMode = (String)hashMap.get("GET_VAL_MODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PRIVILEGE_TYPE");
		return arrayList;
	}

	public String getTableName() {
		return "MM_DATA_PRIVILEGE_RULE";
	}

}
