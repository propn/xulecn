package com.ztesoft.vsop.order.vo;

/**
 * 增值产品，用于订购关系查询接口
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
	private String chargingPolicyCN;//销售品定价策略
	private String Status;
	private String SubscribeTime;//订购关系创建时间
	private String EffDate;
	private String ExpDate;
	private String ChannelPlayerID;//订购渠道
	private String actionType;// 冗余字段(用于拼装订购历史查询的订购动作)
	
	private String productNbr;
	private String prodOfferNbr;
	private String accNbr;//手机号（用于订购历史查询页面展示）
	private String channel_id;//订购渠道id（用于订购历史查询页面，ChannelPlayerID静态数据生效，加一个小写的channel_id）
	private String state_code;//订购历史-处理状态（0处理中,1处理成功，2处理失败）
	private String staff_id;//订购历史-受理人信息 add 20101013 
	
	
	


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

	//---------订购历史查询状态，wo_order_info表的 state_code 状态------
	/** 未启动 */
	public static final String INIT = "10I";

	/** 执行中 */
	public static final String EXECUTING = "10A";

	/** 竣工 */
	public static final String FINISH = "10F";

	/** 异常 */
	public static final String ERROR = "10E";

	/** 回滚 */
	public static final String ROLLBACK = "10C";

	/** 回单失败, 需人工回单 */
	public static final String MANUAL_FEEDBACK = "10D";
	
	/** 人工回正常 **/
	public static final String MANUAL_FEEDBACK_SUCC = "10M";
	
	/** 人工回异常 **/
	public static final String MANUAL_FEEDBACK_FAIL = "10N";





	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	// -------------------------- entity field
	
	
	
	
}
