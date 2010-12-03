package com.ztesoft.vsop.engine.vo;

import java.util.Date;

public class SecondConfirmMsgVO {
	private String rqcode;
	private String custOrderId;
	private String requestXml;
	private String createDate;
	private String threadId;
	private String state;
	private String smsMsg;
	private String dealTime;
	private String resultDesc;
	private String accNbr;
	private String rqresult;
	private String replyTime;
	private String replyCount;
	private String partitionId;
	
	public SecondConfirmMsgVO(String rqcode, String custOrderId, String requestXml,
			String createDate, String threadId, String state, String smsMsg,
			String dealTime, String resultDesc, String accNbr, String rqresult,
			String replyTime, String replyCount, String partitionId) {
		this.rqcode = rqcode;
		this.custOrderId = custOrderId;
		this.requestXml = requestXml;
		this.createDate = createDate;
		this.threadId = threadId;
		this.state = state;
		this.smsMsg = smsMsg;
		this.dealTime = dealTime;
		this.resultDesc = resultDesc;
		this.accNbr = accNbr;
		this.rqresult = rqresult;
		this.replyTime = replyTime;
		this.replyCount = replyCount;
		this.partitionId = partitionId;
	}

	public String getRqcode() {
		return rqcode;
	}

	public void setRqcode(String rqcode) {
		this.rqcode = rqcode;
	}

	public String getCustOrderId() {
		return custOrderId;
	}

	public void setCustOrderId(String custOrderId) {
		this.custOrderId = custOrderId;
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSmsMsg() {
		return smsMsg;
	}

	public void setSmsMsg(String smsMsg) {
		this.smsMsg = smsMsg;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public String getRqresult() {
		return rqresult;
	}

	public void setRqresult(String rqresult) {
		this.rqresult = rqresult;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

}
