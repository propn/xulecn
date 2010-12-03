package com.ztesoft.oaas.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class CPromotionVO extends ValueObject implements VO {

	private String promotionId = "";//促销人ID
	private String promotionCode = "";//促销人编码
	private String promotionName = "";//促销人姓名
	private String partyRoleId = "";//关联参与人标识
	private String promotionType = "";//促销人类型
	private String lanId = "";//本地网ID
	private String lanName = "";//本地网名称
	private String orgId = "";//所属组织ID
	private String orgName = "";//所属组织名称
	private String ifSysOper = "" ;//IF_SYS_OPER
	private String state = "";
	private String promotionItemStr = "";
	
	private String contactName = "";//联系人
	private String contactPhone = "";//联系电话
	private String shortName = "";//+简拼
	private String comments = "";//+描述
	private String operId = "";//录入人
	private String createDate = "";//录入日期
	private String teamName = "";//班组
	private String postName = "";//岗位

	public CPromotionVO() {}

	public CPromotionVO( String ppromotionId, String ppromotionCode, String ppromotionName, String porgId, String poperId, String pcreateDate, String ppartyRoleId, String ppromotionType ) {
		promotionId = ppromotionId;
		promotionCode = ppromotionCode;
		promotionName = ppromotionName;
		orgId = porgId;
		operId = poperId;
		createDate = pcreateDate;
		partyRoleId = ppartyRoleId;
		promotionType = ppromotionType;
	}
    
	public String getLanId() {
		return lanId;
	}
	
	public void setLanId(String pLanId) {
		lanId = pLanId;
	}
	
	public String getLanName() {
		return lanName;
	}
	
	public void setLanName(String pLanName) {
		lanName = pLanName;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String pContactName) {
		contactName = pContactName;
	}
	
	public String getContactPhone() {
		return contactPhone;
	}
	
	public void setContactPhone(String pContactPhone) {
		contactPhone = pContactPhone;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String pShortName) {
		shortName = pShortName;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String pComments) {
		comments = pComments;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String pTeamName) {
		teamName = pTeamName;
	}
	
	public String getPostName() {
		return postName;
	}
	
	public void setPostName(String pPostName) {
		postName = pPostName;
	}
	
	public String getPromotionId() {
		return promotionId;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getOperId() {
		return operId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionId(String pPromotionId) {
		promotionId = pPromotionId;
	}

	public void setPromotionCode(String pPromotionCode) {
		promotionCode = pPromotionCode;
	}

	public void setPromotionName(String pPromotionName) {
		promotionName = pPromotionName;
	}

	public void setOrgId(String pOrgId) {
		orgId = pOrgId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setPartyRoleId(String pPartyRoleId) {
		partyRoleId = pPartyRoleId;
	}

	public void setPromotionType(String pPromotionType) {
		promotionType = pPromotionType;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PROMOTION_ID",this.promotionId);
		hashMap.put("PROMOTION_CODE",this.promotionCode);
		hashMap.put("PROMOTION_NAME",this.promotionName);
		hashMap.put("ORG_ID",this.orgId);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("PROMOTION_TYPE",this.promotionType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.promotionId = (String) hashMap.get("PROMOTION_ID");
			this.promotionCode = (String) hashMap.get("PROMOTION_CODE");
			this.promotionName = (String) hashMap.get("PROMOTION_NAME");
			this.orgId = (String) hashMap.get("ORG_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.promotionType = (String) hashMap.get("PROMOTION_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PROMOTION_ID");
		return arrayList;
	}

	public String getTableName() {
		return "C_PROMOTION";
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getIfSysOper() {
		return ifSysOper;
	}

	public void setIfSysOper(String ifSysOper) {
		this.ifSysOper = ifSysOper;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return Returns the promotionItemStr.
	 */
	public String getPromotionItemStr() {
		return promotionItemStr;
	}

	/**
	 * @param promotionItemStr The promotionItemStr to set.
	 */
	public void setPromotionItemStr(String promotionItemStr) {
		this.promotionItemStr = promotionItemStr;
	}

}
