package com.ztesoft.crm.business.common.specs.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;


public class DcProdPropVO extends ValueObject implements VO {
	

	private String prodClass;
	

	private String itemCode;
	

	private String itemName;
	

	private String itemSeq;
	

	private String dataType;
	

	private String dataLen;
	

	private String isNull;
	

	private String origin;
	

	private String dataServ;
	

	private String needCheck;
	

	private String checkServ;
	

	private String defaultValue;
	

	private String defaultName;
	

	private String editEnable;
	

	private String regularExp;
	

	private String invalidMsg;
	

	private String defaultStyle;
	
	private String package_id;
	
	private String sells_id;
	
	private String bagId;//套餐规格
	
	private String compagesId;//组合构件
	
	private String productId;//产品实例
	
	private String mainProdType; //主产品的产品类型
	
	private String prodType;//附属产品类型
	
	private String showValue;//取值
	
	private String value;//值
	
	private String itemType;//值类型
	
	private String showMsgText="";//显示校验信息
	
	private String dropDownValues="";//下拉值串
	
	private String dropDownLabels="";//下拉值显示名称
	
	
	private String attrValues="";//下拉值串
	
	private String attrValuesDesc="";//下拉值显示名称
	
	private String propType;
	
	private String displayFlag;

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}

	public String getBagId() {
		return bagId;
	}

	public void setBagId(String bagId) {
		this.bagId = bagId;
	}

	public String getCompagesId() {
		return compagesId;
	}

	public void setCompagesId(String compagesId) {
		this.compagesId = compagesId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String getDropDownLabels() {
		return dropDownLabels;
	}

	public void setDropDownLabels(String dropDownLabels) {
		this.dropDownLabels = dropDownLabels;
	}

	public String getDropDownValues() {
		return dropDownValues;
	}

	public void setDropDownValues(String dropDownValues) {
		this.dropDownValues = dropDownValues;
	}

	public String getShowMsgText() {
		return showMsgText;
	}

	public void setShowMsgText(String showMsgText) {
		this.showMsgText = showMsgText;
	}

	public String getShowValue() {
		return showValue;
	}

	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}

	public String getProdClass() {
		return prodClass;
	}
	
	public void setProdClass(String prodClass) {
		this.prodClass = prodClass;
	}
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemSeq() {
		return itemSeq;
	}
	
	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataLen() {
		return dataLen;
	}
	
	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
	public String getIsNull() {
		return isNull;
	}
	
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDataServ() {
		return dataServ;
	}
	
	public void setDataServ(String dataServ) {
		this.dataServ = dataServ;
	}
	public String getNeedCheck() {
		return needCheck;
	}
	
	public void setNeedCheck(String needCheck) {
		this.needCheck = needCheck;
	}
	public String getCheckServ() {
		return checkServ;
	}
	
	public void setCheckServ(String checkServ) {
		this.checkServ = checkServ;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDefaultName() {
		return defaultName;
	}
	
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}
	public String getEditEnable() {
		return editEnable;
	}
	
	public void setEditEnable(String editEnable) {
		this.editEnable = editEnable;
	}
	public String getRegularExp() {
		return regularExp;
	}
	
	public void setRegularExp(String regularExp) {
		this.regularExp = regularExp;
	}
	public String getInvalidMsg() {
		return invalidMsg;
	}
	
	public void setInvalidMsg(String invalidMsg) {
		this.invalidMsg = invalidMsg;
	}
	public String getDefaultStyle() {
		return defaultStyle;
	}
	
	public void setDefaultStyle(String defaultStyle) {
		this.defaultStyle = defaultStyle;
	}

	public String getMainProdType() {
		return mainProdType;
	}

	public void setMainProdType(String mainProdType) {
		this.mainProdType = mainProdType;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				hashMap.put("PROD_CLASS",this.prodClass);	
				hashMap.put("ITEM_CODE",this.itemCode);	
				hashMap.put("ITEM_NAME",this.itemName);	
				hashMap.put("ITEM_SEQ",this.itemSeq);	
				hashMap.put("DATA_TYPE",this.dataType);	
				hashMap.put("DATA_LEN",this.dataLen);	
				hashMap.put("IS_NULL",this.isNull);	
				hashMap.put("ORIGIN",this.origin);	
				hashMap.put("DATA_SERVICE",this.dataServ);	
				hashMap.put("NEED_CHECK",this.needCheck);	
				hashMap.put("CHECK_SERVICE",this.checkServ);	
				hashMap.put("DEFAULT_VALUE",this.defaultValue);	
				hashMap.put("DEFAULT_NAME",this.defaultName);	
				hashMap.put("EDIT_ENABLE",this.editEnable);	
				hashMap.put("REGULAR_EXP",this.regularExp);	
				hashMap.put("INVALID_MSG",this.invalidMsg);	
				hashMap.put("DEFAULT_STYLE",this.defaultStyle);	
				hashMap.put("PROP_TYPE",this.propType);	
				hashMap.put("DISPLAY_FLAG",this.displayFlag);
				return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						this.prodClass = (String) hashMap.get("PROD_CLASS");
						this.itemCode = (String) hashMap.get("ITEM_CODE");
						this.itemName = (String) hashMap.get("ITEM_NAME");
						this.itemSeq = (String) hashMap.get("ITEM_SEQ");
						this.dataType = (String) hashMap.get("DATA_TYPE");
						this.dataLen = (String) hashMap.get("DATA_LEN");
						this.isNull = (String) hashMap.get("IS_NULL");
						this.origin = (String) hashMap.get("ORIGIN");
						this.dataServ = (String) hashMap.get("DATA_SERVICE");
						this.needCheck = (String) hashMap.get("NEED_CHECK");
						this.checkServ = (String) hashMap.get("CHECK_SERVICE");
						this.defaultValue = (String) hashMap.get("DEFAULT_VALUE");
						this.defaultName = (String) hashMap.get("DEFAULT_NAME");
						this.editEnable = (String) hashMap.get("EDIT_ENABLE");
						this.regularExp = (String) hashMap.get("REGULAR_EXP");
						this.invalidMsg = (String) hashMap.get("INVALID_MSG");
						this.defaultStyle = (String) hashMap.get("DEFAULT_STYLE");
						this.propType = (String) hashMap.get("PROP_TYPE");
						this.displayFlag = (String) hashMap.get("DISPLAY_FLAG");
					}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "DC_PROD_PROP";
	}

	public String getAttrValues() {
		return attrValues;
	}

	public void setAttrValues(String attrValues) {
		this.attrValues = attrValues;
	}

	public String getAttrValuesDesc() {
		return attrValuesDesc;
	}

	public void setAttrValuesDesc(String attrValuesDesc) {
		this.attrValuesDesc = attrValuesDesc;
	}

	public String getPackage_id() {
		return package_id;
	}

	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}

	public String getSells_id() {
		return sells_id;
	}

	public void setSells_id(String sells_id) {
		this.sells_id = sells_id;
	}
	
}
