package com.ztesoft.vsop.order.vo;

/**
 * ��ֵ��Ʒ�����ڶ�����ϵ��ѯ�ӿ�
 * @author yi.chengfeng Apr 13, 2010 3:10:43 PM
 *
 */
public class VproducInfo {
	
	private String VproductID;
	private String VproductName;
	private String SPID;
	private String SPName;
	private String ProductOfferType;
	private String ProductOfferID;
	private String ProductOfferName;
	private String chargingPolicyCN;//����Ʒ���۲���
	private String Status;
	private String SubscribeTime;//������ϵ����ʱ��
	private String EffDate;
	private String ExpDate;
	private String ChannelPlayerID;//��������
	private String actionType;// �����ֶ�(����ƴװ������ʷ��ѯ�Ķ�������)
	
	private String productNbr;
	private String prodOfferNbr;
	private String accNbr;//�ֻ��ţ����ڶ�����ʷ��ѯҳ��չʾ��
	private String channel_id;//��������id�����ڶ�����ʷ��ѯҳ�棬ChannelPlayerID��̬������Ч����һ��Сд��channel_id��
	private String state_code;//������ʷ-����״̬��0������,1����ɹ���2����ʧ�ܣ�
	private String staff_id;//������ʷ-��������Ϣ add 20101013 
	
	
	


	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public String getProductNbr() {
		return productNbr;
	}

	public void setProductNbr(String productNbr) {
		this.productNbr = productNbr;
	}

	public String getProdOfferNbr() {
		return prodOfferNbr;
	}

	public void setProdOfferNbr(String prodOfferNbr) {
		this.prodOfferNbr = prodOfferNbr;
	}

	public VproducInfo() {
		super();
	}
	
	public String getVproductID() {
		return VproductID;
	}
	public void setVproductID(String vproductID) {
		VproductID = vproductID;
	}
	public String getVproductName() {
		return VproductName;
	}
	public void setVproductName(String vproductName) {
		VproductName = vproductName;
	}
	public String getSPID() {
		return SPID;
	}
	public void setSPID(String spid) {
		SPID = spid;
	}
	public String getSPName() {
		return SPName;
	}
	public void setSPName(String name) {
		SPName = name;
	}
	public String getProductOfferType() {
		return ProductOfferType;
	}
	public void setProductOfferType(String productOfferType) {
		ProductOfferType = productOfferType;
	}
	public String getProductOfferID() {
		return ProductOfferID;
	}
	public void setProductOfferID(String productOfferID) {
		ProductOfferID = productOfferID;
	}
	public String getProductOfferName() {
		return ProductOfferName;
	}
	public void setProductOfferName(String productOfferName) {
		ProductOfferName = productOfferName;
	}
	public String getChargingPolicyCN() {
		return chargingPolicyCN;
	}
	public void setChargingPolicyCN(String chargingPolicyCN) {
		this.chargingPolicyCN = chargingPolicyCN;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getSubscribeTime() {
		return SubscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		SubscribeTime = subscribeTime;
	}
	public String getEffDate() {
		return EffDate;
	}
	public void setEffDate(String effDate) {
		EffDate = effDate;
	}
	public String getExpDate() {
		return ExpDate;
	}
	public void setExpDate(String expDate) {
		ExpDate = expDate;
	}
	public String getChannelPlayerID() {
		return ChannelPlayerID;
	}
	public void setChannelPlayerID(String channelPlayerID) {
		ChannelPlayerID = channelPlayerID;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	//---------������ʷ��ѯ״̬��wo_order_info��� state_code ״̬------
	/** δ���� */
	public static final String INIT = "10I";

	/** ִ���� */
	public static final String EXECUTING = "10A";

	/** ���� */
	public static final String FINISH = "10F";

	/** �쳣 */
	public static final String ERROR = "10E";

	/** �ع� */
	public static final String ROLLBACK = "10C";

	/** �ص�ʧ��, ���˹��ص� */
	public static final String MANUAL_FEEDBACK = "10D";
	
	/** �˹������� **/
	public static final String MANUAL_FEEDBACK_SUCC = "10M";
	
	/** �˹����쳣 **/
	public static final String MANUAL_FEEDBACK_FAIL = "10N";





	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	// -------------------------- entity field
	
	
	
	
}
