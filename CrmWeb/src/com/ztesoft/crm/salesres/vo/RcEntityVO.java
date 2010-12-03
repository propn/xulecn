package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcEntityVO extends ValueObject implements VO {

	private String rescInstanceId = "";
	private String salesRescId = "";
	private String rescInstanceCode = "";
	private String rescLevel = "";
	private String rescKind = "";
	private String lanId = "";
	private String ownerType = "";
	private String ownerId = "";
	private String storageId = "";
	private String rescState = "";
	private String state = "";
	private String createDate = "";
	private String effDate = "";
	private String expDate = "";
	private String pkCalbody = "";
	private String cinventoryid = "";
	private String vbatchcode = "";

	private String lvEffDate = "";//long型的生效时间
	private String lvExpDate = "";//long型的失效时间
	private String lvCurrent = "";//long型的当前时间
        private String usage = "";
	public RcEntityVO() {}

	public RcEntityVO( String prescInstanceId, String psalesRescId, String prescInstanceCode, String prescLevel, String prescKind, String planId, String pownerType, String pownerId, String pstorageId, String prescState, String pstate, String pcreateDate, String peffDate, String pexpDate, String ppkCalbody, String pcinventoryid, String pvbatchcode ) {
		rescInstanceId = prescInstanceId;
		salesRescId = psalesRescId;
		rescInstanceCode = prescInstanceCode;
		rescLevel = prescLevel;
		rescKind = prescKind;
		lanId = planId;
		ownerType = pownerType;
		ownerId = pownerId;
		storageId = pstorageId;
		rescState = prescState;
		state = pstate;
		createDate = pcreateDate;
		effDate = peffDate;
		expDate = pexpDate;
		pkCalbody = ppkCalbody;
		cinventoryid = pcinventoryid;
		vbatchcode = pvbatchcode;
	}

	public String getRescInstanceId() {
		return rescInstanceId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getRescInstanceCode() {
		return rescInstanceCode;
	}

	public String getRescLevel() {
		return rescLevel;
	}

	public String getRescKind() {
		return rescKind;
	}

	public String getLanId() {
		return lanId;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getRescState() {
		return rescState;
	}

	public String getState() {
		return state;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getPkCalbody() {
		return pkCalbody;
	}

	public String getCinventoryid() {
		return cinventoryid;
	}

	public String getVbatchcode() {
		return vbatchcode;
	}

	public void setRescInstanceId(String pRescInstanceId) {
		rescInstanceId = pRescInstanceId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setRescInstanceCode(String pRescInstanceCode) {
		rescInstanceCode = pRescInstanceCode;
	}

	public void setRescLevel(String pRescLevel) {
		rescLevel = pRescLevel;
	}

	public void setRescKind(String pRescKind) {
		rescKind = pRescKind;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setOwnerType(String pOwnerType) {
		ownerType = pOwnerType;
	}

	public void setOwnerId(String pOwnerId) {
		ownerId = pOwnerId;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setRescState(String pRescState) {
		rescState = pRescState;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public void setPkCalbody(String pPkCalbody) {
		pkCalbody = pPkCalbody;
	}

	public void setCinventoryid(String pCinventoryid) {
		cinventoryid = pCinventoryid;
	}

	public void setVbatchcode(String pVbatchcode) {
		vbatchcode = pVbatchcode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("RESOURCE_INSTANCE_ID",this.rescInstanceId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("RESOURCE_INSTANCE_CODE",this.rescInstanceCode);
		hashMap.put("RESOURCE_LEVEL",this.rescLevel);
		hashMap.put("RESOURCE_KIND",this.rescKind);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("OWNER_TYPE",this.ownerType);
		hashMap.put("OWNER_ID",this.ownerId);
		hashMap.put("STORAGE_ID",this.storageId);
		hashMap.put("RESOURCE_STATE",this.rescState);
		hashMap.put("STATE",this.state);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("PK_CALBODY",this.pkCalbody);
		hashMap.put("CINVENTORYID",this.cinventoryid);
		hashMap.put("VBATCHCODE",this.vbatchcode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.rescInstanceCode = (String) hashMap.get("RESOURCE_INSTANCE_CODE");
			this.rescLevel = (String) hashMap.get("RESOURCE_LEVEL");
			this.rescKind = (String) hashMap.get("RESOURCE_KIND");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.ownerType = (String) hashMap.get("OWNER_TYPE");
			this.ownerId = (String) hashMap.get("OWNER_ID");
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.rescState = (String) hashMap.get("RESOURCE_STATE");
			this.state = (String) hashMap.get("STATE");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.pkCalbody = (String) hashMap.get("PK_CALBODY");
			this.cinventoryid = (String) hashMap.get("CINVENTORYID");
			this.vbatchcode = (String) hashMap.get("VBATCHCODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("RESOURCE_INSTANCE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_ENTITY";
	}

	public String getLvCurrent() {
		return lvCurrent;
	}

	public void setLvCurrent(String lvCurrent) {
		this.lvCurrent = lvCurrent;
	}

	public String getLvEffDate() {
		return lvEffDate;
	}

	public void setLvEffDate(String lvEffDate) {
		this.lvEffDate = lvEffDate;
	}

	public String getLvExpDate() {
		return lvExpDate;
	}

  public String getUsage() {
    return usage;
  }

  public void setLvExpDate(String lvExpDate) {
		this.lvExpDate = lvExpDate;
	}

  public void setUsage(String usage) {
    this.usage = usage;
  }

}
