package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class AgStorageStatisticsVO extends ValueObject implements VO {

	private String agentId = "";
	private String agentName = "";
	private String agentKindCode = "";
	private String agentCode = "";
	private String departId = "";
	private String areaId = "";
	private String regionId = "";
	private String lanId = "";
	private String storageId = "";
    private String salesRescName = "";
    private String rescInstanceCode = "";
    private String seqNo = "";

	public AgStorageStatisticsVO() {}

	public AgStorageStatisticsVO( String pagentId, String pagentName, String pagentKindCode, String pagentCode, String pdepartId, String pareaId, String pregionId, String planId, String pstorageId ) {
		agentId = pagentId;
		agentName = pagentName;
		agentKindCode = pagentKindCode;
		agentCode = pagentCode;
		departId = pdepartId;
		areaId = pareaId;
		regionId = pregionId;
		lanId = planId;
		storageId = pstorageId;
	}

	public String getAgentId() {
		return agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getAgentKindCode() {
		return agentKindCode;
	}

	public String getAgentCode() {
		return agentCode;
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

	public String getLanId() {
		return lanId;
	}

	public String getStorageId() {
		return storageId;
	}

    public String getSalesRescName() {
        return salesRescName;
    }

    public String getRescInstanceCode() {
        return rescInstanceCode;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setAgentId(String pAgentId) {
		agentId = pAgentId;
	}

	public void setAgentName(String pAgentName) {
		agentName = pAgentName;
	}

	public void setAgentKindCode(String pAgentKindCode) {
		agentKindCode = pAgentKindCode;
	}

	public void setAgentCode(String pAgentCode) {
		agentCode = pAgentCode;
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

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

    public void setSalesRescName(String salesRescName) {
        this.salesRescName = salesRescName;
    }

    public void setRescInstanceCode(String rescInstanceCode) {
        this.rescInstanceCode = rescInstanceCode;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("AGENT_ID",this.agentId);
		hashMap.put("AGENT_NAME",this.agentName);
		hashMap.put("AGENT_KIND_CODE",this.agentKindCode);
		hashMap.put("AGENT_CODE",this.agentCode);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("AREA_ID",this.areaId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("STORAGE_ID",this.storageId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.agentId = (String) hashMap.get("AGENT_ID");
			this.agentName = (String) hashMap.get("AGENT_NAME");
			this.agentKindCode = (String) hashMap.get("AGENT_KIND_CODE");
			this.agentCode = (String) hashMap.get("AGENT_CODE");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.areaId = (String) hashMap.get("AREA_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.storageId = (String) hashMap.get("STORAGE_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "AG_STORAGE_STATISTICS";
	}

}
