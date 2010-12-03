package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class InfRcToCardVO extends ValueObject implements VO {

	private String seqId = "";
	private String salesRescId = "";
	private String rescType = "";
	private String salesNum = "";
	private String produceId = "";
	private String produceNo = "";
	private String acceptDate = "";
	private String state = "";
	private String stateMsg = "";
	private String ts = "";
	private String invokeTime = "";
	private String backTime = "";
	private String inflag = "";
	private String nmny = "";
	private String operId = "";
	private String operName = "";
	private String storageid = "";

	public InfRcToCardVO() {}

	public InfRcToCardVO( String pseqId, String psalesRescId, String prescType, String psalesNum, String pproduceId, String pproduceNo, String pacceptDate, String pstate, String pstateMsg, String pts, String pinvokeTime, String pbackTime, String pinflag, String pnmny, String poperId, String poperName, String pstorageid ) {
		seqId = pseqId;
		salesRescId = psalesRescId;
		rescType = prescType;
		salesNum = psalesNum;
		produceId = pproduceId;
		produceNo = pproduceNo;
		acceptDate = pacceptDate;
		state = pstate;
		stateMsg = pstateMsg;
		ts = pts;
		invokeTime = pinvokeTime;
		backTime = pbackTime;
		inflag = pinflag;
		nmny = pnmny;
		operId = poperId;
		operName = poperName;
		storageid = pstorageid;
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

	public String getProduceId() {
		return produceId;
	}

	public String getProduceNo() {
		return produceNo;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public String getState() {
		return state;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public String getTs() {
		return ts;
	}

	public String getInvokeTime() {
		return invokeTime;
	}

	public String getBackTime() {
		return backTime;
	}

	public String getInflag() {
		return inflag;
	}

	public String getNmny() {
		return nmny;
	}

	public String getOperId() {
		return operId;
	}

	public String getOperName() {
		return operName;
	}

	public String getStorageid() {
		return storageid;
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

	public void setProduceId(String pProduceId) {
		produceId = pProduceId;
	}

	public void setProduceNo(String pProduceNo) {
		produceNo = pProduceNo;
	}

	public void setAcceptDate(String pAcceptDate) {
		acceptDate = pAcceptDate;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateMsg(String pStateMsg) {
		stateMsg = pStateMsg;
	}

	public void setTs(String pTs) {
		ts = pTs;
	}

	public void setInvokeTime(String pInvokeTime) {
		invokeTime = pInvokeTime;
	}

	public void setBackTime(String pBackTime) {
		backTime = pBackTime;
	}

	public void setInflag(String pInflag) {
		inflag = pInflag;
	}

	public void setNmny(String pNmny) {
		nmny = pNmny;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setOperName(String pOperName) {
		operName = pOperName;
	}

	public void setStorageid(String pStorageid) {
		storageid = pStorageid;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SEQ_ID",this.seqId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("RESOURCE_TYPE",this.rescType);
		hashMap.put("SALES_NUM",this.salesNum);
		hashMap.put("PRODUCE_ID",this.produceId);
		hashMap.put("PRODUCE_NO",this.produceNo);
		hashMap.put("ACCEPT_DATE",this.acceptDate);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_MSG",this.stateMsg);
		hashMap.put("TS",this.ts);
		hashMap.put("INVOKE_TIME",this.invokeTime);
		hashMap.put("BACK_TIME",this.backTime);
		hashMap.put("INFLAG",this.inflag);
		hashMap.put("NMNY",this.nmny);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("OPER_NAME",this.operName);
		hashMap.put("STORAGEID",this.storageid);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.seqId = (String) hashMap.get("SEQ_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.rescType = (String) hashMap.get("RESOURCE_TYPE");
			this.salesNum = (String) hashMap.get("SALES_NUM");
			this.produceId = (String) hashMap.get("PRODUCE_ID");
			this.produceNo = (String) hashMap.get("PRODUCE_NO");
			this.acceptDate = (String) hashMap.get("ACCEPT_DATE");
			this.state = (String) hashMap.get("STATE");
			this.stateMsg = (String) hashMap.get("STATE_MSG");
			this.ts = (String) hashMap.get("TS");
			this.invokeTime = (String) hashMap.get("INVOKE_TIME");
			this.backTime = (String) hashMap.get("BACK_TIME");
			this.inflag = (String) hashMap.get("INFLAG");
			this.nmny = (String) hashMap.get("NMNY");
			this.operId = (String) hashMap.get("OPER_ID");
			this.operName = (String) hashMap.get("OPER_NAME");
			this.storageid = (String) hashMap.get("STORAGEID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "INF_RC_TO_CARD";
	}

}
