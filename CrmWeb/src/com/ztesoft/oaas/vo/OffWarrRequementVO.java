package com.ztesoft.oaas.vo;

import com.ztesoft.common.valueobject.ValueObject;

public class OffWarrRequementVO extends ValueObject {
	private String privId = "";
	private String offId = "";
	private String offName = "";
	private String warrContent = "";
	private String warrMode = "";
	private String warrValue = "";
	private String restrictServices = "";
	private String warrLen = "";
	private String uniteAcctRequest = "";
	private String maxWarrNum = "";
	private String maxUniteAcctNum = "";
	private String offWarrRequementId = "";
	public String getMaxUniteAcctNum() {
		return maxUniteAcctNum;
	}
	public void setMaxUniteAcctNum(String maxUniteAcctNum) {
		this.maxUniteAcctNum = maxUniteAcctNum;
	}
	public String getMaxWarrNum() {
		return maxWarrNum;
	}
	public void setMaxWarrNum(String maxWarrNum) {
		this.maxWarrNum = maxWarrNum;
	}
	public String getOffId() {
		return offId;
	}
	public void setOffId(String offId) {
		this.offId = offId;
	}
	public String getOffWarrRequementId() {
		return offWarrRequementId;
	}
	public void setOffWarrRequementId(String offWarrRequementId) {
		this.offWarrRequementId = offWarrRequementId;
	}
	public String getRestrictServices() {
		return restrictServices;
	}
	public void setRestrictServices(String restrictServices) {
		this.restrictServices = restrictServices;
	}
	public String getUniteAcctRequest() {
		return uniteAcctRequest;
	}
	public void setUniteAcctRequest(String uniteAcctRequest) {
		this.uniteAcctRequest = uniteAcctRequest;
	}
	public String getWarrContent() {
		return warrContent;
	}
	public void setWarrContent(String warrContent) {
		this.warrContent = warrContent;
	}
	public String getWarrLen() {
		return warrLen;
	}
	public void setWarrLen(String warrLen) {
		this.warrLen = warrLen;
	}
	public String getWarrMode() {
		return warrMode;
	}
	public void setWarrMode(String warrMode) {
		this.warrMode = warrMode;
	}
	public String getWarrValue() {
		return warrValue;
	}
	public void setWarrValue(String warrValue) {
		this.warrValue = warrValue;
	}
	public String getOffName() {
		return offName;
	}
	public void setOffName(String offName) {
		this.offName = offName;
	}
	public String getPrivId() {
		return privId;
	}
	public void setPrivId(String privId) {
		this.privId = privId;
	}
}
