package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class BonusConsLogDetaVO extends ValueObject implements VO {

	private String detaId = "";
	private String giftCertiId = "";
	private String changeTicketId = "";
	private String consGiftCerti = "";
	private String oldQuantity = "";
	private String bonusServCerti = "";
	private String state = "";
	private String stateDate = "";
	private String lanId = "";
	private String operId = "";
	private String createDate = "";
	private String batchId = "";
	private String custId = "";
	private String giftRuleId = "";
	private String objType = "";
	private String objId = "";
	private String actCode = "";
        private String lastRate = "";

	public BonusConsLogDetaVO() {}

	public BonusConsLogDetaVO( String pdetaId, String pgiftCertiId, String pchangeTicketId, String pconsGiftCerti, String poldQuantity, String pbonusServCerti, String pstate, String pstateDate, String planId, String poperId, String pcreateDate, String pbatchId, String pcustId, String pgiftRuleId, String pobjType, String pobjId, String pactCode, String plastRate ) {
		detaId = pdetaId;
		giftCertiId = pgiftCertiId;
		changeTicketId = pchangeTicketId;
		consGiftCerti = pconsGiftCerti;
		oldQuantity = poldQuantity;
		bonusServCerti = pbonusServCerti;
		state = pstate;
		stateDate = pstateDate;
		lanId = planId;
		operId = poperId;
		createDate = pcreateDate;
		batchId = pbatchId;
		custId = pcustId;
		giftRuleId = pgiftRuleId;
		objType = pobjType;
		objId = pobjId;
		actCode = pactCode;
                lastRate = plastRate;
	}

	public String getDetaId() {
		return detaId;
	}

	public String getGiftCertiId() {
		return giftCertiId;
	}

	public String getChangeTicketId() {
		return changeTicketId;
	}

	public String getConsGiftCerti() {
		return consGiftCerti;
	}

	public String getOldQuantity() {
		return oldQuantity;
	}

	public String getBonusServCerti() {
		return bonusServCerti;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getLanId() {
		return lanId;
	}

	public String getOperId() {
		return operId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getBatchId() {
		return batchId;
	}

	public String getCustId() {
		return custId;
	}

	public String getGiftRuleId() {
		return giftRuleId;
	}

	public String getObjType() {
		return objType;
	}

	public String getObjId() {
		return objId;
	}

	public String getActCode() {
		return actCode;
	}

	public void setDetaId(String pDetaId) {
		detaId = pDetaId;
	}

	public void setGiftCertiId(String pGiftCertiId) {
		giftCertiId = pGiftCertiId;
	}

	public void setChangeTicketId(String pChangeTicketId) {
		changeTicketId = pChangeTicketId;
	}

	public void setConsGiftCerti(String pConsGiftCerti) {
		consGiftCerti = pConsGiftCerti;
	}

	public void setOldQuantity(String pOldQuantity) {
		oldQuantity = pOldQuantity;
	}

	public void setBonusServCerti(String pBonusServCerti) {
		bonusServCerti = pBonusServCerti;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setBatchId(String pBatchId) {
		batchId = pBatchId;
	}

	public void setCustId(String pCustId) {
		custId = pCustId;
	}

	public void setGiftRuleId(String pGiftRuleId) {
		giftRuleId = pGiftRuleId;
	}

	public void setObjType(String pObjType) {
		objType = pObjType;
	}

	public void setObjId(String pObjId) {
		objId = pObjId;
	}

	public void setActCode(String pActCode) {
		actCode = pActCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("DETAIL_ID",this.detaId);
		hashMap.put("GIFT_CERTI_ID",this.giftCertiId);
		hashMap.put("CHANGE_TICKET_ID",this.changeTicketId);
		hashMap.put("CONS_GIFT_CERTI",this.consGiftCerti);
		hashMap.put("OLD_QUANTITY",this.oldQuantity);
		hashMap.put("BONUS_SERV_CERTI",this.bonusServCerti);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("BATCH_ID",this.batchId);
		hashMap.put("CUST_ID",this.custId);
		hashMap.put("GIFT_RULE_ID",this.giftRuleId);
		hashMap.put("OBJ_TYPE",this.objType);
		hashMap.put("OBJ_ID",this.objId);
		hashMap.put("ACT_CODE",this.actCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.detaId = (String) hashMap.get("DETAIL_ID");
			this.giftCertiId = (String) hashMap.get("GIFT_CERTI_ID");
			this.changeTicketId = (String) hashMap.get("CHANGE_TICKET_ID");
			this.consGiftCerti = (String) hashMap.get("CONS_GIFT_CERTI");
			this.oldQuantity = (String) hashMap.get("OLD_QUANTITY");
			this.bonusServCerti = (String) hashMap.get("BONUS_SERV_CERTI");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.batchId = (String) hashMap.get("BATCH_ID");
			this.custId = (String) hashMap.get("CUST_ID");
			this.giftRuleId = (String) hashMap.get("GIFT_RULE_ID");
			this.objType = (String) hashMap.get("OBJ_TYPE");
			this.objId = (String) hashMap.get("OBJ_ID");
			this.actCode = (String) hashMap.get("ACT_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("DETAIL_ID");
		return arrayList;
	}

	public String getTableName() {
		return "BONUS_CONS_LOG_DETAIL";
	}

        public String getPropertyValues(){
          return " detaId = " + detaId
                  +" ;giftCertiId = " + giftCertiId
                  +" ;changeTicketId = " + changeTicketId
                  +" ;consGiftCerti = " + consGiftCerti
                  +" ;oldQuantity = " + oldQuantity
                  +" ;bonusServCerti = " + bonusServCerti
                  +" ;state = " + state
                  +" ;stateDate = " + stateDate
                  +" ;lanId = " + lanId
                  +" ;operId = " + operId
                  +" ;createDate = " + createDate
                  +" ;batchId = " + batchId
                  +" ;custId = " + custId
                  +" ;giftRuleId = " + giftRuleId
                  +" ;objType = " + objType
                  +" ;objId = " + objId
                  +" ;actCode = " + actCode;
        }
  public String getLastRate() {
    return lastRate;
  }
  public void setLastRate(String lastRate) {
    this.lastRate = lastRate;
  }

}
