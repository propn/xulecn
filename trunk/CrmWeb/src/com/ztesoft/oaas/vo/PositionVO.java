package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PositionVO extends ValueObject implements VO, XMLItem {

	private String positionId = "";
	private String positionName = "";
	private String positionDesc = "";
	private String state = "";
	private String stateDate = "";

	public PositionVO() {}

	public PositionVO( String ppositionId, String ppositionName, String ppositionDesc, String pstate, String pstateDate ) {
		positionId = ppositionId;
		positionName = ppositionName;
		positionDesc = ppositionDesc;
		state = pstate;
		stateDate = pstateDate;
	}

	public String getPositionId() {
		return positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public String getPositionDesc() {
		return positionDesc;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public void setPositionId(String pPositionId) {
		positionId = pPositionId;
	}

	public void setPositionName(String pPositionName) {
		positionName = pPositionName;
	}

	public void setPositionDesc(String pPositionDesc) {
		positionDesc = pPositionDesc;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("POSITION_ID",this.positionId);
		hashMap.put("POSITION_NAME",this.positionName);
		hashMap.put("POSITION_DESC",this.positionDesc);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.positionId = (String) hashMap.get("POSITION_ID");
			this.positionName = (String) hashMap.get("POSITION_NAME");
			this.positionDesc = (String) hashMap.get("POSITION_DESC");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("POSITION_ID");
		return arrayList;
	}

	public String getTableName() {
		return "POSITION";
	}

    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("positionId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.positionId));
        sbXml.append("' ");
        sbXml.append("positionName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.positionName));
        sbXml.append("' ");
        sbXml.append("positionDesc='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.positionDesc));
        sbXml.append("' ");
        sbXml.append("state='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.state));
        sbXml.append("' ");
        sbXml.append("stateDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.stateDate));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }

    public String pathInTree() {
        return null;
    }

}
