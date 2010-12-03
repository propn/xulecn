package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class InflRcToCardVO extends ValueObject implements VO {

	private String seqId = "";
	private String salesRescId = "";
	private String rescType = "";
	private String salesNum = "";
	private String nmny = "";
	private String infflag = "";
	private String operId = "";
	private String operName = "";
	private String atime = "";
	private String ctime = "";
	private String itime = "";
	private String state = "";
	private String stateMsg = "";

	public InflRcToCardVO() {}

	public InflRcToCardVO( String pseqId, String psalesRescId, String prescType, String psalesNum, String pnmny, String pinfflag, String poperId, String poperName, String patime, String pctime, String pitime, String pstate, String pstateMsg ) {
		seqId = pseqId;
		salesRescId = psalesRescId;
		rescType = prescType;
		salesNum = psalesNum;
		nmny = pnmny;
		infflag = pinfflag;
		operId = poperId;
		operName = poperName;
		atime = patime;
		ctime = pctime;
		itime = pitime;
		state = pstate;
		stateMsg = pstateMsg;
	}

	public String getSeqId() {
		return seqId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getRescType() {
		return rescType;
	}

	public String getSalesNum() {
		return salesNum;
	}

	public String getNmny() {
		return nmny;
	}

	public String getInfflag() {
		return infflag;
	}

	public String getOperId() {
		return operId;
	}

	public String getOperName() {
		return operName;
	}

	public String getAtime() {
		return atime;
	}

	public String getCtime() {
		return ctime;
	}

	public String getItime() {
		return itime;
	}

	public String getState() {
		return state;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public void setSeqId(String pSeqId) {
		seqId = pSeqId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setRescType(String pRescType) {
		rescType = pRescType;
	}

	public void setSalesNum(String pSalesNum) {
		salesNum = pSalesNum;
	}

	public void setNmny(String pNmny) {
		nmny = pNmny;
	}

	public void setInfflag(String pInfflag) {
		infflag = pInfflag;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setOperName(String pOperName) {
		operName = pOperName;
	}

	public void setAtime(String pAtime) {
		atime = pAtime;
	}

	public void setCtime(String pCtime) {
		ctime = pCtime;
	}

	public void setItime(String pItime) {
		itime = pItime;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateMsg(String pStateMsg) {
		stateMsg = pStateMsg;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SEQ_ID",this.seqId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("RESOURCE_TYPE",this.rescType);
		hashMap.put("SALES_NUM",this.salesNum);
		hashMap.put("NMNY",this.nmny);
		hashMap.put("INFFLAG",this.infflag);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("OPER_NAME",this.operName);
		hashMap.put("ATIME",this.atime);
		hashMap.put("CTIME",this.ctime);
		hashMap.put("ITIME",this.itime);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_MSG",this.stateMsg);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.seqId = (String) hashMap.get("SEQ_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.rescType = (String) hashMap.get("RESOURCE_TYPE");
			this.salesNum = (String) hashMap.get("SALES_NUM");
			this.nmny = (String) hashMap.get("NMNY");
			this.infflag = (String) hashMap.get("INFFLAG");
			this.operId = (String) hashMap.get("OPER_ID");
			this.operName = (String) hashMap.get("OPER_NAME");
			this.atime = (String) hashMap.get("ATIME");
			this.ctime = (String) hashMap.get("CTIME");
			this.itime = (String) hashMap.get("ITIME");
			this.state = (String) hashMap.get("STATE");
			this.stateMsg = (String) hashMap.get("STATE_MSG");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "INFL_RC_TO_CARD";
	}

}
