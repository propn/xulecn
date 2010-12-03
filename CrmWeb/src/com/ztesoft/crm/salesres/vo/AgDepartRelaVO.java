package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class AgDepartRelaVO extends ValueObject implements VO {

	private String relaId = "";
	private String agentId = "";
	private String departId = "";
    private String agentName = "";

	public AgDepartRelaVO() {}

	public AgDepartRelaVO( String prelaId, String pagentId, String pdepartId ) {
		relaId = prelaId;
		agentId = pagentId;
		departId = pdepartId;
	}

	public String getRelaId() {
		return relaId;
	}

	public String getAgentId() {
		return agentId;
	}

	public String getDepartId() {
		return departId;
	}

    public String getAgentName() {
        return agentName;
    }

    public void setRelaId(String pRelaId) {
		relaId = pRelaId;
	}

	public void setAgentId(String pAgentId) {
		agentId = pAgentId;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("RELA_ID",this.relaId);
		hashMap.put("AGENT_ID",this.agentId);
		hashMap.put("DEPART_ID",this.departId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.relaId = (String) hashMap.get("RELA_ID");
			this.agentId = (String) hashMap.get("AGENT_ID");
			this.departId = (String) hashMap.get("DEPART_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "AG_DEPART_RELA";
	}

}
