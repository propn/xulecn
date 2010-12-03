package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class AgSrStatisticsVO extends ValueObject implements VO {

	private String orderId = "";
	private String appType = "";
	private String endTime = "";
	private String actAmount = "";
	private String agentName = "";
	private String salesRescName = "";
	private String state = "";
	private String agentKindCode = "";
	private String realPrice = "";
	private String agentId = "";
	private String departId = "";
	private String areaId = "";
	private String regionId = "";
	private String salesRescId = "";
	private String lanId = "";
	private String operName = "";
	private String acceptType = "";
	private String appTypeName = "";
    private String seqNo = "";
    private String rescInstanceCode = "";
    private String rescState = "";

	public AgSrStatisticsVO() {}

	public AgSrStatisticsVO( String porderId, String pappType, String pendTime, String pactAmount, String pagentName, String psalesRescName, String pstate, String pagentKindCode, String prealPrice, String pagentId, String pdepartId, String pareaId, String pregionId, String psalesRescId, String planId, String poperName, String pacceptType, String pappTypeName ) {
		orderId = porderId;
		appType = pappType;
		endTime = pendTime;
		actAmount = pactAmount;
		agentName = pagentName;
		salesRescName = psalesRescName;
		state = pstate;
		agentKindCode = pagentKindCode;
		realPrice = prealPrice;
		agentId = pagentId;
		departId = pdepartId;
		areaId = pareaId;
		regionId = pregionId;
		salesRescId = psalesRescId;
		lanId = planId;
		operName = poperName;
		acceptType = pacceptType;
		appTypeName = pappTypeName;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getAppType() {
		return appType;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getActAmount() {
		return actAmount;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getSalesRescName() {
		return salesRescName;
	}

	public String getState() {
		return state;
	}

	public String getAgentKindCode() {
		return agentKindCode;
	}

	public String getRealPrice() {
		return realPrice;
	}

	public String getAgentId() {
		return agentId;
	}

	public String getDepartId() {
		return departId;
	}

	public String getAreaId() {
		return areaId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getLanId() {
		return lanId;
	}

	public String getOperName() {
		return operName;
	}

	public String getAcceptType() {
		return acceptType;
	}

	public String getAppTypeName() {
		return appTypeName;
	}

    public String getSeqNo() {
        return seqNo;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public String getRescState() {
        return rescState;
    }

    public void setOrderId(String pOrderId) {
		orderId = pOrderId;
	}

	public void setAppType(String pAppType) {
		appType = pAppType;
	}

	public void setEndTime(String pEndTime) {
		endTime = pEndTime;
	}

	public void setActAmount(String pActAmount) {
		actAmount = pActAmount;
	}

	public void setAgentName(String pAgentName) {
		agentName = pAgentName;
	}

	public void setSalesRescName(String pSalesRescName) {
		salesRescName = pSalesRescName;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setAgentKindCode(String pAgentKindCode) {
		agentKindCode = pAgentKindCode;
	}

	public void setRealPrice(String pRealPrice) {
		realPrice = pRealPrice;
	}

	public void setAgentId(String pAgentId) {
		agentId = pAgentId;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setAreaId(String pAreaId) {
		areaId = pAreaId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setOperName(String pOperName) {
		operName = pOperName;
	}

	public void setAcceptType(String pAcceptType) {
		acceptType = pAcceptType;
	}

	public void setAppTypeName(String pAppTypeName) {
		appTypeName = pAppTypeName;
	}

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public void setRescInstanceCode(String rescInstanceCode) {
        this.rescInstanceCode = rescInstanceCode;
    }

    public void setRescState(String rescState) {
        this.rescState = rescState;
    }

    public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORDER_ID",this.orderId);
		hashMap.put("APP_TYPE",this.appType);
		hashMap.put("END_TIME",this.endTime);
		hashMap.put("ACT_AMOUNT",this.actAmount);
		hashMap.put("AGENT_NAME",this.agentName);
		hashMap.put("SALES_RESOURCE_NAME",this.salesRescName);
		hashMap.put("STATE",this.state);
		hashMap.put("AGENT_KIND_CODE",this.agentKindCode);
		hashMap.put("REAL_PRICE",this.realPrice);
		hashMap.put("AGENT_ID",this.agentId);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("AREA_ID",this.areaId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("OPER_NAME",this.operName);
		hashMap.put("ACCEPT_TYPE",this.acceptType);
		hashMap.put("APP_TYPE_NAME",this.appTypeName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orderId = (String) hashMap.get("ORDER_ID");
			this.appType = (String) hashMap.get("APP_TYPE");
			this.endTime = (String) hashMap.get("END_TIME");
			this.actAmount = (String) hashMap.get("ACT_AMOUNT");
			this.agentName = (String) hashMap.get("AGENT_NAME");
			this.salesRescName = (String) hashMap.get("SALES_RESOURCE_NAME");
			this.state = (String) hashMap.get("STATE");
			this.agentKindCode = (String) hashMap.get("AGENT_KIND_CODE");
			this.realPrice = (String) hashMap.get("REAL_PRICE");
			this.agentId = (String) hashMap.get("AGENT_ID");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.areaId = (String) hashMap.get("AREA_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.operName = (String) hashMap.get("OPER_NAME");
			this.acceptType = (String) hashMap.get("ACCEPT_TYPE");
			this.appTypeName = (String) hashMap.get("APP_TYPE_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "AG_SR_STATISTICS";
	}

}
