package com.ztesoft.vsop.engine.vo;

/**
 * ������ֵ��Ʒ
 * @author cooltan
 *
 */
public class VproductInfo {
	private String actionType ;//��ֵ��Ʒ����
	private String vProductId ;//��ֵ��Ʒ��ʶ
	private String vProductNbr ;//��ֵ��Ʒ����
	private String oldVProductId ;//���滻��ֵ��Ʒ��ʶ
	private String effDate ;//��Чʱ��
	private String expDate ;//ʧЧʱ��
	private String vProductInstID ;//������ϵʵ����ʶ
	private String dbActionType;//0���� ���Բ�д
	private String state="";//����ȷ�ϻ�����Ч ʧЧ
	private String offerNbr ; //�����ֶ�  ��ֵ��Ʒ����������Ʒ����
	private String offerType ;//�����ֶ�  ��ֵ��Ʒ����������Ʒ����
	private String offerId ;//�����ֶ�  ��ֵ��Ʒ����������Ʒ��ʶ
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
	 * �����ֶ�  ��ֵ��Ʒ����������Ʒ����
	 * @return
	 */
	public String getOfferNbr() {
		return offerNbr;
	}
	/**
	 * �����ֶ�  ��ֵ��Ʒ����������Ʒ����
	 * @param offerNbr
	 */
	public void setOfferNbr(String offerNbr) {
		this.offerNbr = offerNbr;
	}
	/**
	 * �����ֶ�  ��ֵ��Ʒ����������Ʒ����
	 * @return
	 */
	public String getOfferType() {
		return offerType;
	}
	/**
	 * �����ֶ�  ��ֵ��Ʒ����������Ʒ����
	 * @param offerType
	 */
	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}
	/**
	 * �����ֶ�  ��ֵ��Ʒ����������Ʒ��ʶ
	 * @return
	 */
	public String getOfferId() {
		return offerId;
	}
	/**
	 * �����ֶ�  ��ֵ��Ʒ����������Ʒ��ʶ
	 * @param offerId
	 */
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	/**
	 * ����ֵ��Ʒת���ɼ���XML��ֵ��Ʒ�ڵ�
	 * @return
	 */
	public String toXmlForSpi() {
		StringBuffer bf = new StringBuffer();
		//���������ֵ��Ʒ����ת��Ϊ������ֵ��Ʒ����0������1�������ײ�2���滻���в�Ʒ3���˶�4���˳��ײ�
		String activeActionType="";
		if("0".equals(this.getActionType())){//����
			activeActionType+="0";
		}else if("1".equals(this.getActionType())){//�˶�
			activeActionType+="3";
		}else if("2".equals(this.getActionType())){//ȫ���˶�
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
