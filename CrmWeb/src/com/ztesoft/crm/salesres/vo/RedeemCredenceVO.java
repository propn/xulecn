package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RedeemCredenceVO extends ValueObject  {
    
	private String changeTicketId = "";
	private String custId = "";
	private String giftId = "";
	
	private String retRuleId = "";
	private String varNum = "";
	private String operType = "";
	private String state = "";
	private String stateDate = "";
	private String oldChangeId = "";
	private String acceptId = "";
	private String atime = "";	
	private String produceId = "";
	private String produceNo = "";
	private String objType = "";
	private String objId = "";
	private String landId = "";
	private String operId = "";
	private String giftName = "";
	private String isRoll = "";
	private String nodeType = "";
	private String rollRate = "";
	private String returnReason = "";
	
	public RedeemCredenceVO()
	{
		
	}
	public RedeemCredenceVO(String changeTicketId,String custId,String giftId,String retRuleId,String varNum,String operType,String state,String stateDate,String oldChangeId,String acceptId,String atime,String produceId,String produceNo,String objType,String objId,String landId,String operId,String giftName,String isRoll,String nodeType,String rollRate,String returnReason)
	{
		this.changeTicketId = changeTicketId;
		this.custId = custId;
		this.giftId = giftId;		
		this.retRuleId = retRuleId;
		this.varNum = varNum;
		this.operType = operType;
		this.state = state;
		this.stateDate = stateDate;
		this.oldChangeId = oldChangeId;
		this.acceptId = acceptId;
		this.atime = atime;	
		this.produceId = produceId;
		this.produceNo = produceNo;
		this.objType = objType;
		this.objId = objId;
		this.landId = landId;
		this.operId = operId;
		this.giftName = giftName;
		this.isRoll = isRoll;
		this.nodeType = nodeType;
		this.rollRate = rollRate;
		this.returnReason = returnReason;
	}
		
	
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getRollRate() {
		return rollRate;
	}
	public void setRollRate(String rollRate) {
		this.rollRate = rollRate;
	}
	public String getIsRoll() {
		return isRoll;
	}
	public void setIsRoll(String isRoll) {
		this.isRoll = isRoll;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getLandId() {
		return landId;
	}
	public void setLandId(String landId) {
		this.landId = landId;
	}
	public String getAcceptId() {
		return acceptId;
	}
	public void setAcceptId(String acceptId) {
		this.acceptId = acceptId;
	}
	public String getAtime() {
		return atime;
	}
	public void setAtime(String atime) {
		this.atime = atime;
	}
	public String getChangeTicketId() {
		return changeTicketId;
	}
	public void setChangeTicketId(String changeTicketId) {
		this.changeTicketId = changeTicketId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getObjType() {
		return objType;
	}
	public void setObjType(String objType) {
		this.objType = objType;
	}
	public String getOldChangeId() {
		return oldChangeId;
	}
	public void setOldChangeId(String oldChangeId) {
		this.oldChangeId = oldChangeId;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getProduceId() {
		return produceId;
	}
	public void setProduceId(String produceId) {
		this.produceId = produceId;
	}
	public String getProduceNo() {
		return produceNo;
	}
	public void setProduceNo(String produceNo) {
		this.produceNo = produceNo;
	}
	public String getRetRuleId() {
		return retRuleId;
	}
	public void setRetRuleId(String retRuleId) {
		this.retRuleId = retRuleId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateDate() {
		return stateDate;
	}
	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}
	public String getVarNum() {
		return varNum;
	}
	public void setVarNum(String varNum) {
		this.varNum = varNum;
	}
	
	
	
}
