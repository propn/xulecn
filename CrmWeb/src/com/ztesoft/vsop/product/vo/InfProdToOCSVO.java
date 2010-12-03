/**
 * 
 */
package com.ztesoft.vsop.product.vo;
/**
 * @desc
 * @author qin.guoquan
 * @date Oct 12, 2010 1:58:01 PM
 */
public class InfProdToOCSVO {

	private String infProdToOcsId; 	//ID
	private String prodMsg;			//报文信息
	private String prodSubType;		//产品类型
	private String prodCode;		//产品编码
	private String opFlag;			//动作
	private String prodSystem;		//来源系统
	private String createDate;		//创建时间
	private String state;			//状态
	private String stateDate;		//更新时间
	private String sendTimes;		//发送次数
	private String resultMsg;		//失败原因
	
	public String getInfProdToOcsId() {
		return infProdToOcsId;
	}
	public void setInfProdToOcsId(String infProdToOcsId) {
		this.infProdToOcsId = infProdToOcsId;
	}
	public String getProdMsg() {
		return prodMsg;
	}
	public void setProdMsg(String prodMsg) {
		this.prodMsg = prodMsg;
	}
	public String getProdSubType() {
		return prodSubType;
	}
	public void setProdSubType(String prodSubType) {
		this.prodSubType = prodSubType;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getOpFlag() {
		return opFlag;
	}
	public void setOpFlag(String opFlag) {
		this.opFlag = opFlag;
	}
	public String getProdSystem() {
		return prodSystem;
	}
	public void setProdSystem(String prodSystem) {
		this.prodSystem = prodSystem;
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
	public String getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(String sendTimes) {
		this.sendTimes = sendTimes;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	
	
}
