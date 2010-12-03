package com.ztesoft.vsop.engine.vo;

/**
 * 订单增值产品
 * @author cooltan
 *
 */
public class VproductInfo {
	private String actionType ;//增值产品动作
	private String vProductId ;//增值产品标识
	private String vProductNbr ;//增值产品编码
	private String oldVProductId ;//被替换增值产品标识
	private String effDate ;//生效时间
	private String expDate ;//失效时间
	private String vProductInstID ;//订购关系实例标识
	private String dbActionType;//0新增 可以不写
	private String state="";//二次确认或者生效 失效
	private String offerNbr ; //冗余字段  增值产品所属的销售品编码
	private String offerType ;//冗余字段  增值产品所属的销售品类型
	private String offerId ;//冗余字段  增值产品所属的销售品标识
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getVProductId() {
		return vProductId;
	}
	public void setVProductId(String productId) {
		vProductId = productId;
	}
	public String getVProductNbr() {
		return vProductNbr;
	}
	public void setVProductNbr(String productNbr) {
		vProductNbr = productNbr;
	}
	public String getOldVProductId() {
		return oldVProductId;
	}
	public void setOldVProductId(String oldVProductId) {
		this.oldVProductId = oldVProductId;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getVProductInstID() {
		return vProductInstID;
	}
	public void setVProductInstID(String productInstID) {
		vProductInstID = productInstID;
	}
	public String getDbActionType() {
		return dbActionType;
	}
	public void setDbActionType(String dbActionType) {
		this.dbActionType = dbActionType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 冗余字段  增值产品所属的销售品编码
	 * @return
	 */
	public String getOfferNbr() {
		return offerNbr;
	}
	/**
	 * 冗余字段  增值产品所属的销售品编码
	 * @param offerNbr
	 */
	public void setOfferNbr(String offerNbr) {
		this.offerNbr = offerNbr;
	}
	/**
	 * 冗余字段  增值产品所属的销售品类型
	 * @return
	 */
	public String getOfferType() {
		return offerType;
	}
	/**
	 * 冗余字段  增值产品所属的销售品类型
	 * @param offerType
	 */
	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}
	/**
	 * 冗余字段  增值产品所属的销售品标识
	 * @return
	 */
	public String getOfferId() {
		return offerId;
	}
	/**
	 * 冗余字段  增值产品所属的销售品标识
	 * @param offerId
	 */
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	/**
	 * 将增值产品转换成激活XML增值产品节点
	 * @return
	 */
	public String toXmlForSpi() {
		StringBuffer bf = new StringBuffer();
		//将受理的增值产品动作转换为激活增值产品动作0：订购1：加入套餐2：替换已有产品3：退订4：退出套餐
		String activeActionType="";
		if("0".equals(this.getActionType())){//订购
			activeActionType+="0";
		}else if("1".equals(this.getActionType())){//退订
			activeActionType+="3";
		}else if("2".equals(this.getActionType())){//全部退订
			activeActionType+="3";
		}else{
			activeActionType+=this.getActionType();
		}
		bf 
		.append("<sub_product>")
			.append("<sub_product_code>").append(this.getVProductNbr()).append("</sub_product_code>")
			.append("<oldsub_product_code>").append("").append("</oldsub_product_code>")
			.append("<act_type>").append(activeActionType).append("</act_type>")
			.append("<idtype>").append(this.getOfferType()).append("</idtype>")
			.append("<id>").append(this.getOfferNbr()).append("</id>")
			.append("<prodCharacters>").append("").append("</prodCharacters>")
		.append("</sub_product>");
		return bf.toString();
	}
}
