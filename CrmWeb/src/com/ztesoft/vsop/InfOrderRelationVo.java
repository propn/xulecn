package com.ztesoft.vsop;

/**
 * ������ϵͬ���ӿڱ� inf_order_relation
 * @author qin.guoquan
 *
 */
public class InfOrderRelationVo {

	private String infOrderRelationId;	//��Ϣ��ʶ
	private String userId;				//�û�����
	private String userIdType;			//�û�����
	private String productId;			//����Ʒ��ʶ
	private String packageId;			//��������Ʒ����
	private String opType;				//��������
	private String state;				//״̬
	private String stateDate;			//״̬ʱ��
	private String createDate;			//����ʱ��
	private String sendTimes;			//���ʹ���
	private String resultMsg;          //crm���ر���
	
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
