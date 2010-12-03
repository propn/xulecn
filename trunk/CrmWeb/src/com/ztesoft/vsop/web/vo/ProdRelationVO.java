package com.ztesoft.vsop.web.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class ProdRelationVO extends ValueObject implements VO {

	private String prodRelId = "";
	private String relationTypeCd = "";
	private String prodId = "";
	private String proProdId = "";
	private String stateCd = "";
	private String stateDate = "";
	private String createDate = "";

	public ProdRelationVO() {}

	public ProdRelationVO( String pprodRelId, String prelationTypeCd, String pprodId, String pproProdId, String pstateCd, String pstateDate, String pcreateDate ) {
		prodRelId = pprodRelId;
		relationTypeCd = prelationTypeCd;
		prodId = pprodId;
		proProdId = pproProdId;
		stateCd = pstateCd;
		stateDate = pstateDate;
		createDate = pcreateDate;
	}

	public String getProdRelId() {
		return prodRelId;
	}

	public String getRelationTypeCd() {
		return relationTypeCd;
	}

	public String getProdId() {
		return prodId;
	}

	public String getProProdId() {
		return proProdId;
	}

	public String getStateCd() {
		return stateCd;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setProdRelId(String pProdRelId) {
		prodRelId = pProdRelId;
	}

	public void setRelationTypeCd(String pRelationTypeCd) {
		relationTypeCd = pRelationTypeCd;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setProProdId(String pProProdId) {
		proProdId = pProProdId;
	}

	public void setStateCd(String pStateCd) {
		stateCd = pStateCd;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRODUCT_REL_ID",this.prodRelId);
		hashMap.put("RELATION_TYPE_CD",this.relationTypeCd);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("PRO_PRODUCT_ID",this.proProdId);
		hashMap.put("STATE_CD",this.stateCd);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("CREATE_DATE",this.createDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.prodRelId = (String) hashMap.get("PRODUCT_REL_ID");
			this.relationTypeCd = (String) hashMap.get("RELATION_TYPE_CD");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.proProdId = (String) hashMap.get("PRO_PRODUCT_ID");
			this.stateCd = (String) hashMap.get("STATE_CD");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.createDate = (String) hashMap.get("CREATE_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PRODUCT_REL_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT_RELATION";
	}

}
