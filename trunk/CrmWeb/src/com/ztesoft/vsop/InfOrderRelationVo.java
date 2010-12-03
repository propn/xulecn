package com.ztesoft.vsop;

/**
 * 订购关系同步接口表 inf_order_relation
 * @author qin.guoquan
 *
 */
public class InfOrderRelationVo {

	private String infOrderRelationId;	//消息标识
	private String userId;				//用户号码
	private String userIdType;			//用户类型
	private String productId;			//销售品标识
	private String packageId;			//捆绑销售品编码
	private String opType;				//动作类型
	private String state;				//状态
	private String stateDate;			//状态时间
	private String createDate;			//创建时间
	private String sendTimes;			//发送次数
	private String resultMsg;          //crm返回编码
	
	public String getInfOrderRelationId() {
		return infOrderRelationId;
	}
	public void setInfOrderRelationId(String infOrderRelationId) {
		this.infOrderRelationId = infOrderRelationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserIdType() {
		return userIdType;
	}
	public void setUserIdType(String userIdType) {
		this.userIdType = userIdType;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
