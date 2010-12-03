package com.ztesoft.vsop.order.vo;

public class InfMsg {
	private String infMagId;//INF_MSG_ID,
	private String infMsg;//INF_MSG,
	private String infSystem;//INF_SYSTEM,
	private String createDate;//CREATE_DATE,
	private String state;//STATE,
	private String stateDate;//STATE_DATE
	private String resultMsg;
	public String getInfMagId() {
		return infMagId;
	}
	public void setInfMagId(String infMagId) {
		this.infMagId = infMagId;
	}
	public String getInfMsg() {
		return infMsg;
	}
	public void setInfMsg(String infMsg) {
		this.infMsg = infMsg;
	}
	public String getInfSystem() {
		return infSystem;
	}
	public void setInfSystem(String infSystem) {
		this.infSystem = infSystem;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateDate() {
		return stateDate;
	}
	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
