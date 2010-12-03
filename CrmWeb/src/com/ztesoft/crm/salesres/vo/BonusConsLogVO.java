package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class BonusConsLogVO extends ValueObject implements VO {

	private String bonusConsId = "";
	private String giftCertiId = "";
	private String consGiftCerti = "";
	private String oldQuantity = "";
	private String staffCode = "";
	private String changeTicketId = "";
	private String createDate = "";
	private String state = "";
	private String stateDate = "";
	private String relaConsId = "";
	private String actCode = "";
	private String regionId = "";
	private String macAddr = "";
	private String ipAddr = "";
	private String custId = "";
	private String comments = "";
	private String giftCertiType = "";
	private String custName = "";
	private String changValue = "";
         private String newQuantity = "";

	public BonusConsLogVO() {}

	public BonusConsLogVO( String pbonusConsId, String pgiftCertiId, String pconsGiftCerti, String poldQuantity, String pstaffCode, String pchangeTicketId, String pcreateDate, String pstate, String pstateDate, String prelaConsId, String pactCode, String pregionId, String pmacAddr, String pipAddr, String pcustId, String pcomments ,String pgiftCertiType,String pcustName,String pchangValue) {
		bonusConsId = pbonusConsId;
		giftCertiId = pgiftCertiId;
		consGiftCerti = pconsGiftCerti;
		oldQuantity = poldQuantity;
		staffCode = pstaffCode;
		changeTicketId = pchangeTicketId;
		createDate = pcreateDate;
		state = pstate;
		stateDate = pstateDate;
		relaConsId = prelaConsId;
		actCode = pactCode;
		regionId = pregionId;
		macAddr = pmacAddr;
		ipAddr = pipAddr;
		custId = pcustId;
		comments = pcomments;
	    giftCertiType = pgiftCertiType;
		custName = pcustName;
		changValue = pchangValue;
	}

	public String getBonusConsId() {
		return bonusConsId;
	}

	public String getGiftCertiId() {
		return giftCertiId;
	}

	public String getConsGiftCerti() {
		return consGiftCerti;
	}

	public String getOldQuantity() {
		return oldQuantity;
	}

	public String getStaffCode() {
		return staffCode;
	}

        public String getNewQuantity() {
                return newQuantity;
        }

	public String getChangeTicketId() {
		return changeTicketId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getRelaConsId() {
		return relaConsId;
	}

	public String getActCode() {
		return actCode;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public String getCustId() {
		return custId;
	}

	public String getComments() {
		return comments;
	}

	public void setBonusConsId(String pBonusConsId) {
		bonusConsId = pBonusConsId;
	}

	public void setGiftCertiId(String pGiftCertiId) {
		giftCertiId = pGiftCertiId;
	}

	public void setConsGiftCerti(String pConsGiftCerti) {
		consGiftCerti = pConsGiftCerti;
	}

	public void setOldQuantity(String pOldQuantity) {
		oldQuantity = pOldQuantity;
	}

	public void setStaffCode(String pStaffCode) {
		staffCode = pStaffCode;
	}

	public void setChangeTicketId(String pChangeTicketId) {
		changeTicketId = pChangeTicketId;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setRelaConsId(String pRelaConsId) {
		relaConsId = pRelaConsId;
	}

        public void setNewQuantity(String pNewQuantity) {
                newQuantity = pNewQuantity;
        }


	public void setActCode(String pActCode) {
		actCode = pActCode;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setMacAddr(String pMacAddr) {
		macAddr = pMacAddr;
	}

	public void setIpAddr(String pIpAddr) {
		ipAddr = pIpAddr;
	}

	public void setCustId(String pCustId) {
		custId = pCustId;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("BONUS_CONS_ID",this.bonusConsId);
		hashMap.put("GIFT_CERTI_ID",this.giftCertiId);
		hashMap.put("CONS_GIFT_CERTI",this.consGiftCerti);
		hashMap.put("OLD_QUANTITY",this.oldQuantity);
		hashMap.put("STAFF_CODE",this.staffCode);
		hashMap.put("CHANGE_TICKET_ID",this.changeTicketId);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("RELA_CONS_ID",this.relaConsId);
		hashMap.put("ACT_CODE",this.actCode);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("MAC_ADDRESS",this.macAddr);
		hashMap.put("IP_ADDRESS",this.ipAddr);
		hashMap.put("CUST_ID",this.custId);
		hashMap.put("COMMENTS",this.comments);
		hashMap.put("CUST_NAME",this.custName);
		hashMap.put("CHANGVALUE",this.changValue);
		hashMap.put("GIFT_CERTI_TYPE",this.giftCertiType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.bonusConsId = (String) hashMap.get("BONUS_CONS_ID");
			this.giftCertiId = (String) hashMap.get("GIFT_CERTI_ID");
			this.consGiftCerti = (String) hashMap.get("CONS_GIFT_CERTI");
			this.oldQuantity = (String) hashMap.get("OLD_QUANTITY");
			this.staffCode = (String) hashMap.get("STAFF_CODE");
			this.changeTicketId = (String) hashMap.get("CHANGE_TICKET_ID");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.relaConsId = (String) hashMap.get("RELA_CONS_ID");
			this.actCode = (String) hashMap.get("ACT_CODE");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.macAddr = (String) hashMap.get("MAC_ADDRESS");
			this.ipAddr = (String) hashMap.get("IP_ADDRESS");
			this.custId = (String) hashMap.get("CUST_ID");
			this.comments = (String) hashMap.get("COMMENTS");
			this.custName = (String) hashMap.get("CUST_NAME");
			this.changValue = (String) hashMap.get("CHANGVALUE");
			this.giftCertiType = (String) hashMap.get("GIFT_CERTI_TYPE");

		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "BONUS_CONS_LOG";
	}

	public String getChangValue() {
		return changValue;
	}

	public void setChangValue(String changValue) {
		this.changValue = changValue;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getGiftCertiType() {
		return giftCertiType;
	}

	public void setGiftCertiType(String giftCertiType) {
		this.giftCertiType = giftCertiType;
	}

}
