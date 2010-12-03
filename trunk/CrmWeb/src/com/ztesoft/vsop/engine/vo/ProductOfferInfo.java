package com.ztesoft.vsop.engine.vo;

import java.util.List;

import org.apache.log4j.Logger;
/**
 * ��������Ʒ
 * @author cooltan
 *
 */

public class ProductOfferInfo {
	private static Logger logger = Logger.getLogger(ProductOfferInfo.class);
	private String actioType ;//����Ʒ����
	private String offerType ;//����Ʒ����
	private String offerId ;//����Ʒ��ʶ
	private String offerNbr ;//����Ʒ����
	private String effDate;//��Чʱ��
	private String expDate ;//ʧЧʱ��
	private String dbActionType;//0���������Բ�д
	private String oldOfferId;
	private String offerName;
	//List<VproductInfo>
	private List vproductInfoList;//����Ʒ��ֵ��Ʒ�б�List<VproductInfo>
	public String getActioType() {
		return actioType;
	}
	public void setActioType(String actioType) {
		this.actioType = actioType;
	}
	public String getOfferType() {
		return offerType;
	}
	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getOfferNbr() {
		if(null!=this.offerNbr){
			return offerNbr;
		}else{
			return "";
		}
	}
	public void setOfferNbr(String offerNbr) {
		this.offerNbr = offerNbr;
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
	public String getDbActionType() {
		return dbActionType;
	}
	public void setDbActionType(String dbActionType) {
		this.dbActionType = dbActionType;
	}
	public List getVproductInfoList() {
		return vproductInfoList;
	}
	public void setVproductInfoList(List vproductInfoList) {
		this.vproductInfoList = vproductInfoList;
	}
	public String getOldOfferId() {
		return oldOfferId;
	}
	public void setOldOfferId(String oldOfferId) {
		this.oldOfferId = oldOfferId;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
}
