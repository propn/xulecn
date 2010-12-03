package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcEntityCountVO extends ValueObject implements VO {

	private String lan_id = "";
	private String stock = "";
	private String addnum = "";
	private String salenum = "";
	private String curr_stock = "";
	private String differentnum = "";

	public RcEntityCountVO() {}

	public RcEntityCountVO( String ppartyRoleId, String ppartyRoleName, String ppassword, String ppartyRoleType, String porgManeger, String ppartyId, String pofficeId, String pstate, String peffDate, String pexpDate, String ppwdvalidtype, String pupdateTime, String pincorStarttime, String ploginStatus, String ploginCount, String plimitCount, String pmenuCode, String porgPartyId, String pmemo, String pcreateDate, String psPartyId ,
			String DevUserBelong,String ChannelSegmentId,String StaffDesc) {
//		partyRoleId = ppartyRoleId;
//		partyRoleName = ppartyRoleName;
//		password = ppassword;
//		partyRoleType = ppartyRoleType;
//		orgManeger = porgManeger;
//		partyId = ppartyId;
//		officeId = pofficeId;
//		state = pstate;
//		effDate = peffDate;
//		expDate = pexpDate;
//		pwdvalidtype = ppwdvalidtype;
//		updateTime = pupdateTime;
//		incorStarttime = pincorStarttime;
//		loginStatus = ploginStatus;
//		loginCount = ploginCount;
//		limitCount = plimitCount;
//		menuCode = pmenuCode;
//		orgPartyId = porgPartyId;
//		memo = pmemo;
//		createDate = pcreateDate;
//		sPartyId = psPartyId;
//		devUserBelong = DevUserBelong;
//		 channelSegmentId = ChannelSegmentId;
//		 staffDesc = StaffDesc;
	}




	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("lan_id",this.lan_id);
		hashMap.put("stock",this.stock);
		hashMap.put("addnum",this.addnum);
		hashMap.put("salenum",this.salenum);
		hashMap.put("differentnum",this.differentnum);
		hashMap.put("curr_stock",this.curr_stock);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lan_id = (String) hashMap.get("lan_id");
			this.stock = (String) hashMap.get("stock");
			this.addnum = (String) hashMap.get("addnum");
			this.salenum = (String) hashMap.get("salenum");
			this.differentnum = (String) hashMap.get("differentnum");
			this.curr_stock = (String) hashMap.get("curr_stock");
			
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTY_ROLE";
	}


	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getAddnum() {
		return addnum;
	}

	public void setAddnum(String addnum) {
		this.addnum = addnum;
	}

	public String getSalenum() {
		return salenum;
	}

	public void setSalenum(String salenum) {
		this.salenum = salenum;
	}

	public String getCurr_stock() {
		return curr_stock;
	}

	public void setCurr_stock(String curr_stock) {
		this.curr_stock = curr_stock;
	}

	public String getDifferentnum() {
		return differentnum;
	}

	public void setDifferentnum(String differentnum) {
		this.differentnum = differentnum;
	}

}
