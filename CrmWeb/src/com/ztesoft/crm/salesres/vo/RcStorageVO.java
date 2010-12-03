package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.*;

import java.util.*;

public class RcStorageVO extends ValueObject implements VO {
    
	public static final String TopStorageId = "-1";
	private String rcType = "";
	private String storageId = "";
	private String storageName = "";
	private String ownerId = "";
	private String storageState = "";
	private String storageDesc = "";
	private String addr = "";
	private String storageCode = "";
	private String upStorageId = "";
	private String rStorageCode1 = "";
	private String rStorageCode2 = "";
	private String lan_id = "";
	private String lan_name = "";
	private String c_oper_id = "";
	private String oper_name = "";
	private String c_time = "";
	
    private String upStorageName = "";
    

//    private String storageType = "";

    private int childrenNum = 0;  // 为了仓库树而构造的

	public int getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(int childrenNum) {
		this.childrenNum = childrenNum;
	}

	public String getUpStorageName() {
		return upStorageName;
	}

	public void setUpStorageName(String upStorageName) {
		this.upStorageName = upStorageName;
	}

	public RcStorageVO() {}

	public RcStorageVO( String pstorageId, String pstorageName, String pownerId, String pstorageState, String pstorageDesc, String paddr, String pstorageCode, String pupStorageId, String prStorageCode1, String prStorageCode2 ) {
		storageId = pstorageId;
		storageName = pstorageName;
		ownerId = pownerId;
		storageState = pstorageState;
		storageDesc = pstorageDesc;
		addr = paddr;
		storageCode = pstorageCode;
		upStorageId = pupStorageId;
		rStorageCode1 = prStorageCode1;
		rStorageCode2 = prStorageCode2;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getStorageName() {
		return storageName;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getStorageState() {
		return storageState;
	}

	public String getStorageDesc() {
		return storageDesc;
	}

	public String getAddr() {
		return addr;
	}

	public String getStorageCode() {
		return storageCode;
	}

	public String getUpStorageId() {
		return upStorageId;
	}

	public String getRStorageCode1() {
		return rStorageCode1;
	}

	public String getRStorageCode2() {
		return rStorageCode2;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setStorageName(String pStorageName) {
		storageName = pStorageName;
	}

	public void setOwnerId(String pOwnerId) {
		ownerId = pOwnerId;
	}

	public void setStorageState(String pStorageState) {
		storageState = pStorageState;
	}

	public void setStorageDesc(String pStorageDesc) {
		storageDesc = pStorageDesc;
	}

	public void setAddr(String pAddr) {
		addr = pAddr;
	}

	public void setStorageCode(String pStorageCode) {
		storageCode = pStorageCode;
	}

	public void setUpStorageId(String pUpStorageId) {
		upStorageId = pUpStorageId;
	}

	public void setRStorageCode1(String pRStorageCode1) {
		rStorageCode1 = pRStorageCode1;
	}

	public void setRStorageCode2(String pRStorageCode2) {
		rStorageCode2 = pRStorageCode2;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STORAGE_ID",this.storageId);
		hashMap.put("STORAGE_NAME",this.storageName);
		hashMap.put("OWNER_ID",this.ownerId);
		hashMap.put("STORAGE_STATE",this.storageState);
		hashMap.put("STORAGE_DESC",this.storageDesc);
		hashMap.put("ADDRESS",this.addr);
		hashMap.put("STORAGE_CODE",this.storageCode);
		hashMap.put("UP_STORAGE_ID",this.upStorageId);
		hashMap.put("R_STORAGE_CODE1",this.rStorageCode1);
		hashMap.put("R_STORAGE_CODE2",this.rStorageCode2);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.storageName = (String) hashMap.get("STORAGE_NAME");
			this.ownerId = (String) hashMap.get("OWNER_ID");
			this.storageState = (String) hashMap.get("STORAGE_STATE");
			this.storageDesc = (String) hashMap.get("STORAGE_DESC");
			this.addr = (String) hashMap.get("ADDRESS");
			this.storageCode = (String) hashMap.get("STORAGE_CODE");
			this.upStorageId = (String) hashMap.get("UP_STORAGE_ID");
			this.rStorageCode1 = (String) hashMap.get("R_STORAGE_CODE1");
			this.rStorageCode2 = (String) hashMap.get("R_STORAGE_CODE2");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_STORAGE";
	}
    
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml) {
		sbXml.append("<item ");
		sbXml.append("storageId='");
		sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.storageId));
		sbXml.append("' ");

		sbXml.append("storageName='");
		sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.storageName));
		sbXml.append("' ");

		// sbXml.append("nodeType='");
		// sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.nodeType));
		// sbXml.append("' ");

		sbXml.append("ownerId='");
		sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.ownerId));
		sbXml.append("' ");

		sbXml.append("storageState='");
		sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.storageState));
		sbXml.append("' ");

		sbXml.append("storageCode='");
		sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.storageCode));
		sbXml.append("' ");

		sbXml.append("upStorageId='");
		sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.upStorageId));
		sbXml.append("' ");

		sbXml.append("childrenNum='");
		sbXml.append(XMLSegBuilder.escapeXMLStringValue(String
				.valueOf(this.childrenNum)));
		sbXml.append("' ");

		if (this.childrenNum > 0) {
			// 目录
			sbXml
					.append("IMG_SRC='../../public/skins/bsn/xtree/xp/folder.png' ");
		} else {
			// 叶子
			sbXml.append("IMG_SRC='../../public/skins/bsn/xtree/xp/file.png' ");
		}

		sbXml.append(">");
		return sbXml;
	}

	public String getRcType() {
		return rcType;
	}

	public void setRcType(String rcType) {
		this.rcType = rcType;
	}

	public String getC_time() {
		return c_time;
	}

	public void setC_time(String c_time) {
		this.c_time = c_time;
	}

	public String getLan_name() {
		return lan_name;
	}

	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}

	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getC_oper_id() {
		return c_oper_id;
	}

	public void setC_oper_id(String c_oper_id) {
		this.c_oper_id = c_oper_id;
	}
//
//	public String getStorageType() {
//		return storageType;
//	}
//
//	public void setStorageType(String storageType) {
//		this.storageType = storageType;
//	}

	public static String getTopStorageId() {
		return TopStorageId;
	}
}
